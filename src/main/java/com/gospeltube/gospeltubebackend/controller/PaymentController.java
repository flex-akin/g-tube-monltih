package com.gospeltube.gospeltubebackend.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.gospeltube.gospeltubebackend.dto.*;
import com.gospeltube.gospeltubebackend.exception.ResourceNotFoundException;
import com.gospeltube.gospeltubebackend.service.PaymentService;
import com.gospeltube.gospeltubebackend.util.Utilities;
import com.stripe.exception.StripeException;
import com.stripe.model.AccountLink;
import com.stripe.model.checkout.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;
import com.stripe.model.Account;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/payment")
public class PaymentController {

    private PaymentService paymentService;
    private final JwtDecoder jwtDecoder;

    public PaymentController(PaymentService paymentService, JwtDecoder jwtDecoder) {
        this.paymentService = paymentService;
        this.jwtDecoder = jwtDecoder;
    }

    @PostMapping()
    public PaystackSubaccountResponseDto createSubaccount(
            @RequestBody SubaccountDto subaccountDto){
        return paymentService.createSubAccount(subaccountDto);
    }

    @PostMapping("/makePayment")
    public ResponseEntity<Response> acceptPayment(
            @RequestBody PaystackAceptPaymentDto paystackAceptPaymentDto){
//        Utilities utilities = new Utilities(jwtDecoder);
//        String principal = utilities.getPrincipal(authorizationHeader);
//        PaystackPaymentDto paystackPaymentDto = new PaystackPaymentDto(amount, principal, );
        AcceptPaymentDto acceptPaymentDto = paymentService.acceptPayment(paystackAceptPaymentDto);
        Set<PaymentResponse> payment= new HashSet<>();
        PaymentResponse paymentResponse = new PaymentResponse(acceptPaymentDto.getData().getAuthorization_url());
        payment.add(paymentResponse);
        return new ResponseEntity<>(new Response("00", acceptPaymentDto.getMessage(), true, payment), HttpStatus.OK);
    }

    @PostMapping("/stripe/create")
    public ResponseEntity<Response> acceptPayment(
            @RequestBody StripeAccountDto stripeAccountDto
    ) throws StripeException {
        Account account = this.paymentService.createAccount(stripeAccountDto);

        Set<String> accontSet = new HashSet<>();
        accontSet.add(account.getId());
        return new ResponseEntity<>(
                new Response(
                        "00",
                        "account created",
                        true,
                        accontSet
                ),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/stripe/accountLink/{accountId}")
    public ResponseEntity<Response> generateAccountLink(
          @PathVariable String accountId
    ) throws StripeException {
        AccountLink accountLink = this.paymentService.createAccountLink(accountId);
        Set<String> accountLinkSet = new HashSet<>();
        accountLinkSet.add(accountLink.getUrl());
        return new ResponseEntity<>(
                new Response(
                        "00",
                        "account link generated",
                        true,
                        accountLinkSet
                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/stripe/checkout")
    public ResponseEntity<Response> createCharge(
            @RequestBody PaystackAceptPaymentDto paystackAceptPaymentDto
    ) throws StripeException {
        Session session = this.paymentService.createCharge(paystackAceptPaymentDto);
        Set<String> checkoutLinkSet = new HashSet<>();
        checkoutLinkSet.add(session.getUrl());
        return new ResponseEntity<>(
                new Response(
                        "00",
                        "checkout successful",
                        true,
                        checkoutLinkSet
                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/paystack/webhook")
    public ResponseEntity<String> handleWebhook(
            @RequestBody String rawJson,
            @RequestHeader("x-paystack-signature") String PaystackSignature) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        boolean value = this.paymentService.paystackWebhook(rawJson, PaystackSignature );
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/stripe/webhook")
    public ResponseEntity<String> stripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String signHeader
    ){
        this.paymentService.stripeWebhook(payload, signHeader);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/getTransaction")
    public ResponseEntity<Response> getTransaction(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @RequestParam("frequency") Optional<String> frequency,
            @RequestParam("year") Optional<Integer> year,
            @RequestParam("month") Optional<Integer> month
    ){
        String freq;
        int y = 0;
        int m = 0;
        freq = frequency.orElseThrow(
                () -> new ResourceNotFoundException("frequency not defined")
        );
        if(freq.equals("MONTH")){
            if(year.isPresent()){
                y = year.get();
            }
            else{
                y = LocalDateTime.now().getYear();
            }
        }
        else if(freq.equals("WEEK")){
            if(year.isPresent()){
                y = year.get();
            }
            else{
                y = LocalDateTime.now().getYear();
            }
            if (month.isPresent()) {
                m = month.get();
            }
            else {
                m = LocalDateTime.now().getMonthValue();
            }
        }
        Utilities utilities = new Utilities(jwtDecoder);
        String principal = utilities.getPrincipal(authorizationHeader);
        Set<PaymentData> data = this.paymentService.getPayment(principal, freq, y, m );
        return new ResponseEntity<>(new Response("00", "data fetched succesfully",
                true, data), HttpStatus.OK);
    }
}
