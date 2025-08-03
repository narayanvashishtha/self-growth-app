package com.system_mastery.ascension.service;

import com.system_mastery.ascension.model.Ritual;
import com.system_mastery.ascension.model.RitualLog;
import com.system_mastery.ascension.model.User;
import com.system_mastery.ascension.repository.RitualLogRepository;
import com.system_mastery.ascension.repository.RitualRepository;
import com.system_mastery.ascension.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RitualService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RitualRepository ritualRepository;

    @Autowired
    RitualLogRepository ritualLogRepository;

    public void createRitual(String username, String title){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        Ritual ritual = new Ritual();
        ritual.setUser(user);
        ritual.setTitle(title);

        Ritual savedRitual = ritualRepository.save(ritual);

        List<RitualLog> logs = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for(int i=0 ; i<90 ; i++){
            RitualLog log = new RitualLog();
            log.setDate(today.plusDays(i));
            log.setUser(savedRitual.getUser());
            log.setRitual(savedRitual);
            log.setCompleted(false);
            log.setMissed(false);
            log.setXpPenalty(0);

            logs.add(log);
        }
        ritualLogRepository.saveAll(logs);
    }

    public List<Ritual> getTodayRitualsForUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Invalid Request"));
        LocalDate today = LocalDate.now();

        List<RitualLog> todayRituals = ritualLogRepository.findByDateAndUserId(today, user.getId()).stream()
                                        .filter(ritualLog -> !ritualLog.isCompleted()).toList();

        return todayRituals.stream().map(RitualLog::getRitual).distinct().toList();
    }
}
