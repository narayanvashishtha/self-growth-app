package com.system_mastery.ascension.controller;

import com.system_mastery.ascension.dto.AuthRequestDto;
import com.system_mastery.ascension.dto.AuthResponseDto;
import com.system_mastery.ascension.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequestDto authRequestDto){
        return ResponseEntity.ok(authService.userRegister(authRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto){
        return ResponseEntity.ok(authService.login(authRequestDto));
    }
}
