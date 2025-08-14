package com.system_mastery.ascension.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "Ritual")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ritual {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int xpPerDay;

    private String title;

    private Integer durationDays = 90;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}
