package com.system_mastery.ascension.service;

import com.system_mastery.ascension.dto.AuthRequestDto;
import com.system_mastery.ascension.dto.AuthResponseDto;
import com.system_mastery.ascension.model.Role;
import com.system_mastery.ascension.model.User;
import com.system_mastery.ascension.repository.UserRepository;
import com.system_mastery.ascension.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String userRegister(AuthRequestDto requestDto){

        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        if(userRepository.findByUsername(username).isPresent()){
            throw new RuntimeException("User already exists");
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setUsername(username);
        user.setEmail(requestDto.getEmail());
        user.setPassword(encodedPassword);
        user.setRole(Role.ROLE_USER);

        userRepository.save(user);

        return "You've been registered successfully";
    }

    public AuthResponseDto login(AuthRequestDto requestDto){
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            System.out.println("Authentication successful for: " + username);

            // if no exception thrown → authenticated
            User user = userRepository.findByUsername(username).get();
            String token = jwtTokenProvider.generateToken(user);

            return new AuthResponseDto(token, jwtTokenProvider.getExpirationInMs());

        } catch (Exception e) {
            System.out.println("Authentication failed for " + username + ": " + e.getMessage());
            throw new RuntimeException("Invalid credentials", e);
        }
    }
}
