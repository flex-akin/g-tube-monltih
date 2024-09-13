package com.gospeltube.gospeltubebackend.dto;

import java.util.Objects;

public class SubaccountDto {
    private String business_name;
    private String settlement_bank;
    private String account_number;
    private String email;
    private final double percentage_charge = 98.0;

    public SubaccountDto(String business_name, String settlement_bank, String account_number, String email) {
        this.business_name = business_name;
        this.settlement_bank = settlement_bank;
        this.account_number = account_number;
        this.email = email;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getSettlement_bank() {
        return settlement_bank;
    }

    public void setSettlement_bank(String settlement_bank) {
        this.settlement_bank = settlement_bank;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public double getPercentage_charge() {
        return percentage_charge;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubaccountDto that = (SubaccountDto) o;
        return Objects.equals(business_name, that.business_name) && Objects.equals(settlement_bank, that.settlement_bank) && Objects.equals(account_number, that.account_number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(business_name, settlement_bank, account_number, percentage_charge);
    }
}
