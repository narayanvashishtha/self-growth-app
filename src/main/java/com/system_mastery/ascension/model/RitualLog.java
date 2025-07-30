package com.system_mastery.ascension.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "Ritual log")
@Data
public class RitualLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate date;

    private boolean completed;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    private boolean missed;

    private int xpPenalty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ritual_id", nullable = false)
    private Ritual ritual;
}
