package com.system_mastery.ascension.controller;


import com.system_mastery.ascension.model.RitualLog;
import com.system_mastery.ascension.service.RitualLogService;
import com.system_mastery.ascension.service.RitualService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class RitualController {

    @Autowired
    RitualService ritualService;

    @Autowired
    RitualLogService ritualLogService;


    public ResponseEntity<List<RitualLog>> getTodayRituals(Principal principal){
        String username = principal.getName();
        ritualService.getTodayRitualsForUser(username);
    }

    @PostMapping("/complete/{ritualLogId}")
    public ResponseEntity<String> completeRitual(@PathVariable Long ritualLogId, Principal principal){
        ritualLogService.markRitualAsCompleted(ritualLogId, principal.getName());
    }
}
