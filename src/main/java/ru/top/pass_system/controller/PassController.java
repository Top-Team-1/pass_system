package ru.top.pass_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.top.pass_system.dto.passDTO.PassCreateDTO;
import ru.top.pass_system.dto.passDTO.PassResponseDTO;
import ru.top.pass_system.dto.passDTO.PassUpdateDTO;
import ru.top.pass_system.service.PassService;

import java.util.List;

@RestController
@RequestMapping("/api/pass")
@RequiredArgsConstructor
public class PassController {

    private final PassService passService;

    @PostMapping
    public ResponseEntity<PassResponseDTO> create(@RequestBody PassCreateDTO passCreateDTO) {
        return ResponseEntity.ok(passService.create(passCreateDTO));
    }

    @GetMapping
    public ResponseEntity<List<PassResponseDTO>> findAll() {
        return ResponseEntity.ok(passService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(passService.findById(id));
    }

    @PutMapping
    public ResponseEntity<PassResponseDTO> update(@RequestBody PassUpdateDTO passUpdateDTO) {
        return ResponseEntity.ok(passService.update(passUpdateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        passService.delete(id);
        return ResponseEntity.ok("Пропуск с id: " + id + " успешно удалён.");
    }
}
