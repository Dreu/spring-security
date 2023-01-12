package com.dreu.security.controller;

import com.dreu.security.model.LoginDto;
import com.dreu.security.model.ResponseToken;
import com.dreu.security.model.UserDto;
import com.dreu.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ResponseToken> register(@RequestBody UserDto userDto)
    {
        return ResponseEntity.ok(authService.register(userDto));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseToken> authenticate(@RequestBody LoginDto login)
    {
        return ResponseEntity.ok(authService.authenticate(login));
    }
}
