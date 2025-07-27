package ru.top.pass_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.top.pass_system.dto.userDTO.UserCreateDTO;
import ru.top.pass_system.dto.userDTO.UserResponseDTO;
import ru.top.pass_system.service.UserService;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping
    public ResponseEntity<UserResponseDTO> create (@RequestBody UserCreateDTO userCreateDTO){

        return ResponseEntity.ok(userService.create(userCreateDTO));

    }
}
