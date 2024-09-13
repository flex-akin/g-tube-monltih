package com.gospeltube.gospeltubebackend.dto;

import java.util.UUID;

public class PaystackPaymentDto {
    private String amount;
    private String email;
    private String subaccount;
    private String reference;

    public PaystackPaymentDto(String amount, String email, String subaccount) {
        this.amount = amount;
        this.email = email;
        this.subaccount = subaccount;
        this.reference = generateReference();
    }

    private String generateReference() {
        return UUID.randomUUID().toString();
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubaccount() {
        return subaccount;
    }

    public void setSubaccount(String subaccount) {
        this.subaccount = subaccount;
    }

    public String getReference() {
        return reference;
    }

    @Override
    public String toString() {
        return "PaystackPaymentDto{" +
                "amount='" + amount + '\'' +
                ", email='" + email + '\'' +
                ", subaccount='" + subaccount + '\'' +
                ", reference='" + reference + '\'' +
                '}';
    }
}