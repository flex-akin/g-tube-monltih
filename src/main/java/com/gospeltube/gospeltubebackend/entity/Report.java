package com.gospeltube.gospeltubebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "item_id")
    private Long item_Id;
    @Column(columnDefinition = "TEXT")
    private String message;
    @Column(name="item_type")
    private String itemType;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    public Report() {
    }

    public Report(Long item_Id, String message, String itemType, AppUser user) {
        this.item_Id = item_Id;
        this.message = message;
        this.itemType = itemType;
        this.user = user;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItem_Id() {
        return item_Id;
    }

    public void setItem_Id(Long item_Id) {
        this.item_Id = item_Id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
}
