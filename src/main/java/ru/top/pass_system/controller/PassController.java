package ru.top.pass_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.top.pass_system.dto.passDTO.PassCreateDTO;
import ru.top.pass_system.dto.passDTO.PassResponseDTO;
import ru.top.pass_system.service.PassService;

@RestController
@RequestMapping("/api/pass")
@RequiredArgsConstructor
public class PassController {

    private final PassService passService;

    // Создание нового пропуска
    @PostMapping
    public ResponseEntity<PassResponseDTO> create(@RequestBody PassCreateDTO passCreateDTO) {
        return ResponseEntity.ok(passService.create(passCreateDTO));
    }
}
