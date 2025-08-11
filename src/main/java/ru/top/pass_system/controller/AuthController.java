package ru.top.pass_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.top.pass_system.dto.userDTO.SignInRequest;
import ru.top.pass_system.dto.userDTO.SignUpRequest;
import ru.top.pass_system.security.JwtAuthResponse;
import ru.top.pass_system.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("sign-up")
    public JwtAuthResponse signUp(@RequestBody @Valid SignUpRequest signUpRequest) {

        return authService.signUp(signUpRequest);
    }

    @PostMapping("sign-in")
    public JwtAuthResponse singIn(@RequestBody SignInRequest signInRequest) {

        return authService.signIn(signInRequest);
    }
}
