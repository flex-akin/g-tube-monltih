package com.gospeltube.gospeltubebackend.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gospeltube.gospeltubebackend.dto.*;
import com.gospeltube.gospeltubebackend.entity.Admin;
import com.gospeltube.gospeltubebackend.entity.AppUser;
import com.gospeltube.gospeltubebackend.entity.Church;
import com.gospeltube.gospeltubebackend.entity.Transactions;
import com.gospeltube.gospeltubebackend.exception.BadRequestException;
import com.gospeltube.gospeltubebackend.repository.AdminRepository;
import com.gospeltube.gospeltubebackend.repository.ChurchRepository;
import com.gospeltube.gospeltubebackend.repository.TransactionRepository;
import com.gospeltube.gospeltubebackend.repository.UserRepository;
import com.gospeltube.gospeltubebackend.service.PaymentService;
import com.nimbusds.jose.shaded.gson.JsonSyntaxException;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.AccountCreateParams;
import com.stripe.param.AccountLinkCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Formatter;
import java.util.Set;


@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final UserRepository userRepository;
    private final ChurchRepository churchRepository;
    private final TransactionRepository transactionRepository;
    private final AdminRepository adminRepository;

    public PaymentServiceImpl(WebClient webClient, UserRepository userRepository, ChurchRepository churchRepository, TransactionRepository transactionRepository, AdminRepository adminRepository) {
        this.webClient = webClient;
        this.userRepository = userRepository;
        this.churchRepository = churchRepository;
        this.transactionRepository = transactionRepository;
        this.adminRepository = adminRepository;
    }


    @Value("${stripe.apiKey}")
    private String stripeApiKey;
    @Value("${stripe.returnLink}")
    private  String stripeReturnLink;
    @Value("${stripe.refreshLink}")
    private String stripeRefreshLink;
    @Value("${stripe.percentage}")
    private int applicationFeePercentage;
    @Value("${paystack.secretKey}")
    private String secretKey;
    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    @Override
    public Account createAccount(StripeAccountDto stripeAccountDto) throws StripeException {
            Stripe.apiKey = this.stripeApiKey;
            AccountCreateParams params = AccountCreateParams.builder()
                            .setType(AccountCreateParams.Type.CUSTOM)
                            .setCountry(stripeAccountDto.getCountry())
                            .setEmail(stripeAccountDto.getEmail())
                    .setBusinessType(AccountCreateParams.BusinessType.COMPANY)
                            .setCapabilities(AccountCreateParams.Capabilities.builder().setCardPayments(AccountCreateParams.Capabilities.CardPayments.builder()
                                                    .setRequested(true)
                                                    .build())
                                            .setTransfers(AccountCreateParams.Capabilities.Transfers.builder()
                                                            .setRequested(true)
                                                            .build())
                                    .setBankTransferPayments(AccountCreateParams.Capabilities.BankTransferPayments.builder()
                                            .setRequested(true)
                                            .build())

                                    .build()
                            )
                            .build();
            Account account = Account.create(params);
            AppUser user = this.userRepository.findByEmail(stripeAccountDto.getEmail()).orElseThrow(
                    () -> new BadRequestException("church doesn't exist")
            );
            Church church = user.getChurch() ;
            church.setConnectAccount(account.getId());
            return account;
    }

    @Override
    public AccountLink createAccountLink(String accountId) throws StripeException {
        Stripe.apiKey = this.stripeApiKey;
        AccountLinkCreateParams params =
                AccountLinkCreateParams.builder()
                        .setAccount(accountId)
                        .setRefreshUrl("https://www.web.gospeltube.tv/")
                        .setReturnUrl("https://www.web.gospeltube.tv/")
                        .setType(AccountLinkCreateParams.Type.ACCOUNT_ONBOARDING)
                        .build();
        return AccountLink.create(params);
    }

    @Override
    public Session createCharge(PaystackAceptPaymentDto paystackAceptPaymentDto) throws StripeException {
        Stripe.apiKey = this.stripeApiKey;
        AppUser appUser = this.userRepository.findByEmail(paystackAceptPaymentDto.getEmail()).orElseThrow(
                ()-> new BadRequestException("User not found")
        );
        AppUser churchUser = this.userRepository.findById(paystackAceptPaymentDto.getChurchId()).orElseThrow(
                ()-> new BadRequestException("User not found")
        );

        SessionCreateParams params = SessionCreateParams.builder()
                        .addLineItem(SessionCreateParams.LineItem.builder()
                                .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                                        .setCurrency("usd")
                                                        .setProductData(
                                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                        .setName("Church Donation")
                                                                        .build()
                                                        )
                                                        .setUnitAmount(Long.parseLong(paystackAceptPaymentDto.getAmount()))
                                                        .build()
                                        )
                                        .setQuantity(1L)
                                        .build()
                        )
                        .setPaymentIntentData(
                                SessionCreateParams.PaymentIntentData.builder()
                                        .setApplicationFeeAmount(20L)
                                        .setTransferData(
                                                SessionCreateParams.PaymentIntentData.TransferData.builder()
                                                        .setDestination(churchUser.getChurch().getConnectAccount())
                                                        .build()
                                        )
                                        .build()
                        )
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("https://web.gospeltube.tv/success?session_id={CHECKOUT_SESSION_ID}")
                        .build();
        Session session = Session.create(params);
        String sessionId = session.getId();
        session = Session.retrieve(sessionId);
        String paymentIntentId = session.getPaymentIntent();
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

        Transactions transactions = new Transactions();
        transactions.setAmount(new BigDecimal(paystackAceptPaymentDto.getAmount()));
        transactions.setModule("stripe");
        transactions.setDescription("church donation");
        transactions.setPayer(appUser.getUserId());
        transactions.setReceiver(churchUser.getUserId());
        transactions.setRef(paymentIntent.getId());
        transactions.setType("credit");
        transactions.setStatus("INITIATED");

        return session;
    }

    @Override
    public PaystackSubaccountResponseDto createSubAccount(SubaccountDto subaccountDto) {
        AppUser user = this.userRepository.findByEmail(subaccountDto.getEmail()).orElseThrow(
                () -> new BadRequestException("user doesn't exist")
        );
        Church church = this.churchRepository.findById(user.getUserId()).orElseThrow(
                () -> new BadRequestException("user not found")
        );
        PaystackSubaccountResponseDto response =  webClient.post()
                .uri("/subaccount")
                .bodyValue(subaccountDto)
                .retrieve()
                .bodyToMono(PaystackSubaccountResponseDto.class)
                .block();
        church.setSubaccount(response.getData().getSubaccount_code());
        church.setBank(response.getData().getSettlement_bank());
        church.setAccountNumber(response.getData().getAccount_number());
        churchRepository.save(church);
        return response;
    }

    @Override
    public AcceptPaymentDto acceptPayment(PaystackAceptPaymentDto paystackAceptPaymentDto) {
        AppUser appUser = this.userRepository.findByEmail(paystackAceptPaymentDto.getEmail()).orElseThrow(
                ()-> new BadRequestException("User not found")
        );
        AppUser churchUser = this.userRepository.findById(paystackAceptPaymentDto.getChurchId()).orElseThrow(
                ()-> new BadRequestException("User not found")
        );


        PaystackPaymentDto paystackPaymentDto = new PaystackPaymentDto(
                paystackAceptPaymentDto.getAmount(),
                paystackAceptPaymentDto.getEmail(),
                churchUser.getChurch().getSubaccount()
        );

        Transactions transactions = new Transactions();
        transactions.setAmount(new BigDecimal(paystackAceptPaymentDto.getAmount()));
        transactions.setModule("paystack");
        transactions.setDescription("church donation");
        transactions.setPayer(appUser.getUserId());
        transactions.setReceiver(churchUser.getUserId());
        transactions.setRef(paystackPaymentDto.getReference());
        transactions.setType("credit");
        transactions.setStatus("INITIATED");

        this.transactionRepository.save(transactions);

        return webClient.post()
                .uri("/transaction/initialize")
                .bodyValue(paystackPaymentDto)
                .retrieve()
                .bodyToMono(AcceptPaymentDto.class)
                .block();
    }

    public boolean paystackWebhook(String payload, String paystackSignature) throws NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        String generatedSignature = generateHmacSha512(payload, secretKey);
        if (!generatedSignature.equals(paystackSignature)) {
            JsonNode payloadJson = objectMapper.readTree(payload);
            String event = payloadJson.get("event").asText();
            JsonNode data = payloadJson.get("data");
            JsonNode fees = data.get("fees_split");
            if (event.equals("charge.success")){
                Transactions transactions = this.transactionRepository.findByRef(data.get("reference").asText()).orElseThrow(
                        () -> new BadRequestException("charge not found")
                );
                transactions.setStatus("SUCCESS");
                transactions.setAmount(new BigDecimal(data.get("amount").asInt()));
                transactions.setCurrency(data.get("currency").asText());
                transactions.setTime(OffsetDateTime.parse(data.get("paid_at").asText()).toLocalDateTime());
                transactions.setChannel(data.get("channel").asText());
                transactions.setFees(new BigDecimal(data.get("fees").asInt()));
                transactions.setAmountAfterFees(new BigDecimal(fees.get("subaccount").asInt()));
                transactions.setGtubeFees(new BigDecimal(fees.get("integration").asInt()));

                this.transactionRepository.save(transactions);
                AppUser churchUser = this.userRepository.findById(transactions.getReceiver()).orElseThrow(
                        () -> new BadRequestException("church not found")
                );
                Church church = churchUser.getChurch();
                church.setAmount(church.getAmount().add(new BigDecimal(fees.get("subaccount").asInt())));
                this.churchRepository.save(church);
                Admin admin = this.adminRepository.findById(1).orElseThrow(
                        () -> new BadRequestException("admin not found")
                );
                admin.setAmount(admin.getAmount().add(new BigDecimal(fees.get("integration").asInt())));
                this.adminRepository.save(admin);

            }
        }
        else {
            throw new BadRequestException("invalid");
        }
        return generatedSignature.equals(paystackSignature);
    }


    private String generateHmacSha512(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA512");
        Mac mac = Mac.getInstance("HmacSHA512");
        mac.init(secretKeySpec);
        byte[] hmacData = mac.doFinal(data.getBytes());
        return toHexString(hmacData);
    }

    private String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    public boolean stripeWebhook(String payload, String signHeader ){
        Stripe.apiKey = this.stripeApiKey;
        Event event;
        try {
            event = Webhook.constructEvent(payload, signHeader, this.webhookSecret);
        } catch (JsonSyntaxException | SignatureVerificationException e) {
            // Invalid payload
            throw new BadRequestException("invalid payload");
        }
        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = null;
        if (dataObjectDeserializer.getObject().isPresent()) {
            stripeObject = dataObjectDeserializer.getObject().get();
        } else {
            throw new BadRequestException("deserialization failed");
        }
        switch (event.getType()) {
            case "payment_intent.succeeded":
                PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
                Transactions transactions = this.transactionRepository.findByRef(paymentIntent.getId()).orElseThrow(
                        () -> new BadRequestException("charge not found")
                );
                transactions.setStatus("SUCCESS");
                transactions.setAmount(new BigDecimal(paymentIntent.getAmount()));
                transactions.setCurrency(paymentIntent.getCurrency());
                transactions.setTime(convertEpochToLocalDateTime(paymentIntent.getCreated()));
                transactions.setChannel(paymentIntent.getPaymentMethod());
                transactions.setFees(new BigDecimal(paymentIntent.getApplicationFeeAmount()));
                transactions.setAmountAfterFees(new BigDecimal(paymentIntent.getAmountReceived()));
                transactions.setGtubeFees(new BigDecimal(paymentIntent.getApplicationFeeAmount()));

                break;
        }
        return true;
    }


    private LocalDateTime convertEpochToLocalDateTime (long epochTime) {
        Instant instant = Instant.ofEpochSecond(epochTime);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public Set<PaymentData> getPayment(String email, String frequency, int year, int month){
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new BadRequestException("user not found")
        );
        if(frequency.equals("MONTH")){
            if (year > 2024){
                throw new BadRequestException("invalid year");
            }
            return this.transactionRepository.getTransactionsByMonth(user.getUserId(), year).orElseThrow(
                    ()-> new BadRequestException("data not found")
            );
        }
        else if(frequency.equals("WEEK")) {
            if (year > 2024){
                throw new BadRequestException("invalid year");
            }
            if (month < 1 || month > 12){
                throw new BadRequestException("invalid month");
            }
            return this.transactionRepository.getTransactionByWeeks(user.getUserId(), year, month).orElseThrow(
                    ()-> new BadRequestException("data not found")
            );
        }
        else if(frequency.equals("DAY")){
            return this.transactionRepository.getTransactionsPerDay(user.getUserId()).orElseThrow(
                    ()-> new BadRequestException("data not found")
            );
        }
        else{
            throw new BadRequestException("invalid parameters");
        }
    }


}
