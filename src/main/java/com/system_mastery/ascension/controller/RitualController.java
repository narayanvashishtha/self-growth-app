package com.system_mastery.ascension.controller;


import com.system_mastery.ascension.dto.RitualDto;
import com.system_mastery.ascension.model.Ritual;
import com.system_mastery.ascension.service.RitualLogService;
import com.system_mastery.ascension.service.RitualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class RitualController {

    @Autowired
    RitualService ritualService;

    @Autowired
    RitualLogService ritualLogService;

    @GetMapping("ritual/today")
    public ResponseEntity<List<Ritual>> getTodayRituals(Principal principal){
        String username = principal.getName();
        List<Ritual> todayRitual = ritualService.getTodayRitualsForUser(username);

        return ResponseEntity.ok(todayRitual);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createRitual(@RequestBody RitualDto ritualDto, Principal principal) {
        ritualService.createRitual(principal.getName(), ritualDto.getName());

        return ResponseEntity.ok("Ritual created successfully");
    }


    @PostMapping("/complete/{ritualLogId}")
    public ResponseEntity<String> completeRitual(@PathVariable Long ritualLogId, Principal principal){
        ritualLogService.markRitualAsCompleted(ritualLogId, principal.getName());

        return ResponseEntity.ok("Ritual has been completed");
    }
}
