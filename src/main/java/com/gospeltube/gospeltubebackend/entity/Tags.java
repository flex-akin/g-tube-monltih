package com.gospeltube.gospeltubebackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name="tags")
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tag;
}
