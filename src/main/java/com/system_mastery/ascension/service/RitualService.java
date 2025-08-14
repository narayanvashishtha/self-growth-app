package com.system_mastery.ascension.service;

import com.system_mastery.ascension.model.Ritual;
import com.system_mastery.ascension.model.RitualLog;
import com.system_mastery.ascension.model.User;
import com.system_mastery.ascension.repository.RitualLogRepository;
import com.system_mastery.ascension.repository.RitualRepository;
import com.system_mastery.ascension.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RitualService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RitualRepository ritualRepository;

    @Autowired
    RitualLogRepository ritualLogRepository;

    private static final int TOTAL_XP = 1000;
    private static final int DAYS = 90;

    public void createRitual(String username, List<String> ritualNames){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        int ritualsPerDay = ritualNames.size();
        if (ritualsPerDay == 0) {
            throw new RuntimeException("No rituals provided");
        }

        int totalSlots = DAYS * ritualsPerDay;
        int baseXp = TOTAL_XP / totalSlots;
        int remainder = TOTAL_XP % totalSlots; // extra XP to distribute

        List<RitualLog> logs = new ArrayList<>();
        LocalDate today = LocalDate.now();
        int slotCounter = 0;

        for(String title : ritualNames){
            Ritual ritual = new Ritual();
            ritual.setUser(user);
            ritual.setTitle(title);

            Ritual saved = ritualRepository.save(ritual);

            for(int i=0 ; i<90 ; i++) {
                RitualLog log = new RitualLog();
                log.setDate(today.plusDays(i));
                log.setUser(user);
                log.setRitual(saved);
                log.setCompleted(false);
                log.setMissed(false);

                // Give extra XP to first 'remainder' slots
                int xpForThisLog = baseXp + ((slotCounter < remainder) ? 1 : 0);
                log.setXpValue(xpForThisLog); // XP for completing this log
                log.setXpPenalty(xpForThisLog); // penalty if missed

                logs.add(log);
                slotCounter++;
            }
        }
        ritualLogRepository.saveAll(logs);
    }

    @Transactional(readOnly = true)
    public List<String> getTodayRitualsForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid Request"));
        LocalDate today = LocalDate.now();

        List<RitualLog> todayRituals = ritualLogRepository.findByDateAndUserId(today, user.getId()).stream()
                .filter(ritualLog -> !ritualLog.isCompleted()).toList();

        return todayRituals.stream()
                .map(RitualLog::getRitual)
                .distinct()
                .map(Ritual::getTitle)  // Just get the title/name
                .toList();
    }

    @Transactional
    public void completeRitual(Long ritualLogId, String username) {
        RitualLog ritualLog = ritualLogRepository.findById(ritualLogId)
                .orElseThrow(() -> new RuntimeException("Ritual not found"));

        User user = ritualLog.getUser();

        if (!user.getUsername().equals(username)) {
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
