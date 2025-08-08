package ru.top.pass_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.top.pass_system.dto.userDTO.CurrentUserUpdateDTO;
import ru.top.pass_system.dto.userDTO.UserResponseDTO;
import ru.top.pass_system.model.User;
import ru.top.pass_system.service.CurrentUserService;

@RestController
@RequestMapping("api/me")
@RequiredArgsConstructor
public class CurrentUserController {

    private final CurrentUserService currentUserService;

    @GetMapping
    public ResponseEntity<UserResponseDTO> getCurrentUser(){

        return ResponseEntity.ok(currentUserService.getCurrentUser());
    }

    @PutMapping
    public ResponseEntity<UserResponseDTO> update (CurrentUserUpdateDTO currentUserUpdateDTO){

       return ResponseEntity.ok(currentUserService.update(currentUserUpdateDTO));
    }

}
