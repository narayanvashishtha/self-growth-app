package com.system_mastery.ascension.controller;


import com.system_mastery.ascension.dto.RitualCreationDto;
import com.system_mastery.ascension.model.Ritual;
import com.system_mastery.ascension.service.RitualLogService;
import com.system_mastery.ascension.service.RitualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<List<String>> getTodayRituals(Authentication authentication) {
        String username = authentication.getName();
        List<String> ritualNames = ritualService.getTodayRitualsForUser(username);
        return ResponseEntity.ok(ritualNames);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createRitual(@RequestBody RitualCreationDto ritualCreationDto, Principal principal) {
        ritualService.createRitual(principal.getName(), ritualCreationDto.getRituals());

        return ResponseEntity.ok("Ritual created successfully");
    }


    @PostMapping("/rituals/{ritualLogId}/complete")
    public ResponseEntity<String> completeRitual(@PathVariable Long ritualLogId, Principal principal) {
        ritualService.completeRitual(ritualLogId, principal.getName());
        return ResponseEntity.ok("Ritual completed successfully");
    }

}
