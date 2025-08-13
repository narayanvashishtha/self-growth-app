package com.system_mastery.ascension.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DashboardDto {
    private int totalXp;       // Full XP possible (bar length)
    private int earnedXp;      // XP earned so far (bar fill)
    private int streak;        // Current streak count

    public DashboardDto(int totalXp, int earnedXp, int streak) {
        this.totalXp = totalXp;
        this.earnedXp = earnedXp;
        this.streak = streak;
    }
}

