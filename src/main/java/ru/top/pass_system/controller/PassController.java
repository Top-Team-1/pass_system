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

import java.net.URI;

@RestController
@RequestMapping("/api/pass")
@RequiredArgsConstructor
public class PassController {

    private final PassService passService;

    /**
     * Создание нового пропуска
     */
    @PostMapping
    public ResponseEntity<PassResponseDTO> create(@RequestBody PassCreateDTO passCreateDTO) {
        PassResponseDTO createdPass = passService.create(passCreateDTO);
        URI location = URI.create("/api/pass/" + createdPass.getId());
        return ResponseEntity.created(location).body(createdPass);
    }

    /**
     * Получение списка пропусков с поддержкой фильтрации, сортировки и постраничного вывода
     */
    @GetMapping
    public ResponseEntity<Page<PassResponseDTO>> findAll(@ModelAttribute PassFilterDTO filter,
                                                         @PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(passService.findAll(filter, pageable));
    }

    /**
     * Получение одного пропуска по ID
     */
    @GetMapping("{id}")
    public ResponseEntity<PassResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(passService.findById(id));
    }

    /**
     * Обновление существующего пропуска
     */
    @PutMapping
    public ResponseEntity<PassResponseDTO> update(@RequestBody PassUpdateDTO passUpdateDTO) {
        return ResponseEntity.ok(passService.update(passUpdateDTO));
    }

    /**
     * Удаление пропуска по ID
     */
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        passService.delete(id);
        return ResponseEntity.ok("Пропуск с id: " + id + " успешно удалён.");
    }
}
