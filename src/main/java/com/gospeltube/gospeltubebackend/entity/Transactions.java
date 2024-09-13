package com.gospeltube.gospeltubebackend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ref;
    private LocalDateTime time;
    private String module;
    private String description;
    @Column(length = 10)
    private String type;
    private BigDecimal amount;
    private String status;
    private String channel;
    private String currency;
    private Long payer;
    private Long receiver;
    @Column(name = "amount_after_fees")
    private BigDecimal amountAfterFees;
    @Column(name = "gtube_fees")
    private BigDecimal gtubeFees;
    private BigDecimal fees;

    public Transactions(String ref, LocalDateTime time, String module, String description, String type, BigDecimal amount, String status, String channel, String currency, Long payer, Long receiver) {
        this.ref = ref;
        this.time = time;
        this.module = module;
        this.description = description;
        this.type = type;
        this.amount = amount;
        this.status = status;
        this.channel = channel;
        this.currency = currency;
        this.payer = payer;
        this.receiver = receiver;
    }

    public Transactions() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getPayer() {
        return payer;
    }

    public void setPayer(Long payer) {
        this.payer = payer;
    }

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    public BigDecimal getAmountAfterFees() {
        return amountAfterFees;
    }

    public void setAmountAfterFees(BigDecimal amountAfterFees) {
        this.amountAfterFees = amountAfterFees;
    }

    public BigDecimal getGtubeFees() {
        return gtubeFees;
    }

    public void setGtubeFees(BigDecimal gtubeFees) {
        this.gtubeFees = gtubeFees;
    }

    public BigDecimal getFees() {
        return fees;
    }

    public void setFees(BigDecimal fees) {
        this.fees = fees;
    }
}
