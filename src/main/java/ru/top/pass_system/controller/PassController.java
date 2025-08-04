package ru.top.pass_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.top.pass_system.dto.passDTO.PassCreateDTO;
import ru.top.pass_system.dto.passDTO.PassFilterDTO;
import ru.top.pass_system.dto.passDTO.PassResponseDTO;
import ru.top.pass_system.dto.passDTO.PassUpdateDTO;
import ru.top.pass_system.service.PassService;

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
    public ResponseEntity<Page<PassResponseDTO>> findAll(
            @ModelAttribute PassFilterDTO filter,
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        return ResponseEntity.ok(passService.findAll(filter, pageable));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<PassResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(passService.findById(id));
    }

    @PutMapping
    public ResponseEntity<PassResponseDTO> update(@RequestBody PassUpdateDTO passUpdateDTO) {
        return ResponseEntity.ok(passService.update(passUpdateDTO));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        passService.delete(id);
        return ResponseEntity.ok("Пропуск с id: " + id + " успешно удалён.");
    }
}
