package com.system_mastery.ascension.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "Ritual")
@Data
public class Ritual {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private Integer durationDays = 90;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}
