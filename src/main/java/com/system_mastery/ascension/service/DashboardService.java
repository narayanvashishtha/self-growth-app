package com.system_mastery.ascension.service;

import com.system_mastery.ascension.dto.DashboardDto;
import com.system_mastery.ascension.model.RitualLog;
import com.system_mastery.ascension.model.User;
import com.system_mastery.ascension.repository.RitualLogRepository;
import com.system_mastery.ascension.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserRepository userRepository;
    private final RitualLogRepository ritualLogRepository;

    public DashboardDto getDashboard(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<RitualLog> logs = ritualLogRepository.findByUserId(user.getId());

        int totalXp = logs.stream()
                .mapToInt(RitualLog::getXpValue)
                .sum();

        int earnedXp = logs.stream()
                .filter(RitualLog::isCompleted)
                .mapToInt(RitualLog::getXpValue)
                .sum();

        int streak = calculateStreak(user);

        return new DashboardDto(totalXp, earnedXp, streak);
    }

    private int calculateStreak(User user) {
        LocalDate today = LocalDate.now();
        int streak = 0;

        // Go backwards from today until we find a missed ritual day
        for (int dayOffset = 0; ; dayOffset++) {
            LocalDate checkDate = today.minusDays(dayOffset);
            List<RitualLog> dayLogs = ritualLogRepository.findByDateAndUserId(checkDate, user.getId());

            if (dayLogs.isEmpty() || dayLogs.stream().anyMatch(log -> !log.isCompleted())) {
                break;
            }
            streak++;
        }

        // Streak only counts if >= 2 days completed fully
        return streak >= 2 ? streak : 0;
    }
}
