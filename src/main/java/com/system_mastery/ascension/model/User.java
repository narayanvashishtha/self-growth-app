package com.system_mastery.ascension.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true, nullable = false)
    @Setter
    private String username;

    @Setter
    private String password;

    @Column(unique = true)
    @Setter
    private String email;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @Setter
    private Role role;

}
