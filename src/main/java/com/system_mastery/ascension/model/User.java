package com.system_mastery.ascension.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    @Setter
    private String username;

    @Setter
    private String password;

    @Setter
    private int xp = 0;

    @Column(unique = true)
    @Setter
    private String email;

    @Setter
    private int streak;

    @Setter
    private LocalDate lastStreakDate;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @Setter
    private Role role;

}
