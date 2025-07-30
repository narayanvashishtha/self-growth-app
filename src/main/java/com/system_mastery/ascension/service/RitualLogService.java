package com.system_mastery.ascension.service;


import com.system_mastery.ascension.model.Ritual;
import com.system_mastery.ascension.model.RitualLog;
import com.system_mastery.ascension.model.User;
import com.system_mastery.ascension.repository.RitualLogRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
@Service
public class RitualLogService {

    @Autowired
    RitualLogRepository ritualLogRepository;

    // Scheduled to run every day at 00:00 midnight
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void markMissedRituals(){

        LocalDate today = LocalDate.now();

        List<RitualLog> uncompletedLogs = ritualLogRepository.findByDateAndCompletedFalse(today);

        for(RitualLog logEntry : uncompletedLogs){
            logEntry.setMissed(true);
            logEntry.setXpPenalty(20);
        }
        ritualLogRepository.saveAll(uncompletedLogs);
        log.info("Marked {} rituals as missed for date {}", uncompletedLogs.size(), today);
    }

    public void markRitualAsCompleted(Long ritualLogId, String name){
        RitualLog ritualLog = ritualLogRepository.findById(ritualLogId)
                .orElseThrow(() -> new RuntimeException("Ritual not found"));

        Ritual ritual = ritualLog.getRitual();
        User user = ritual.getUser();

        if (!user.getUsername().equals(name)) {
            throw new SecurityException("You are not allowed to modify this ritual");
        }

        if (ritualLog.isCompleted()) {
            throw new IllegalStateException("Ritual already completed");
        }

        ritualLog.setCompleted(true);
        ritualLog.setCompletedAt(LocalDateTime.now());
        ritualLogRepository.save(ritualLog);
    }
}
