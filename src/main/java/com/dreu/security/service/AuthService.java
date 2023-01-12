package com.dreu.security.service;

import com.dreu.security.config.JwtService;
import com.dreu.security.enumeration.Role;
import com.dreu.security.model.LoginDto;
import com.dreu.security.model.ResponseToken;
import com.dreu.security.model.UserDto;
import com.dreu.security.repository.IUserRepository;
import com.dreu.security.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseToken register(UserDto request) {
        var user = User.builder()
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.USER)
            .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return ResponseToken.builder()
            .token(jwtToken)
            .build();
    }

    public ResponseToken authenticate(LoginDto request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        var user = userRepository.findByEmail(request.getEmail())
            .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return ResponseToken.builder()
            .token(jwtToken)
            .build();
    }
}
