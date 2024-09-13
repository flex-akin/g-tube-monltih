package com.gospeltube.gospeltubebackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gospeltube.gospeltubebackend.dto.*;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;
import com.stripe.model.checkout.Session;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Set;

public interface PaymentService {
    Account createAccount (StripeAccountDto stripeAccountDto) throws StripeException;
    AccountLink createAccountLink(String accountId) throws StripeException;
    Session createCharge(PaystackAceptPaymentDto paystackAceptPaymentDto) throws StripeException;
    PaystackSubaccountResponseDto createSubAccount(SubaccountDto subaccountDto);
    AcceptPaymentDto acceptPayment(PaystackAceptPaymentDto paystackPaymentDto);
    boolean stripeWebhook(String payload, String signHeader);
    boolean paystackWebhook(String rawJson, String paystackSignature) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException;
    Set<PaymentData> getPayment(String email, String frequency, int year, int month);
}
