package com.gospeltube.gospeltubebackend.dto;

public class SubacctountData {
    private String subaccount_code;
    private String account_number;
    private String settlement_bank;

    public SubacctountData(String subaccount_code, String account_number, String settlement_bank) {
        this.subaccount_code = subaccount_code;
        this.account_number = account_number;
        this.settlement_bank = settlement_bank;
    }

    public String getSubaccount_code() {
        return subaccount_code;
    }

    public void setSubaccount_code(String subaccount_code) {
        this.subaccount_code = subaccount_code;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getSettlement_bank() {
        return settlement_bank;
    }

    public void setSettlement_bank(String settlement_bank) {
        this.settlement_bank = settlement_bank;
    }
}
