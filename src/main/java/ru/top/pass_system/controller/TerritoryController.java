package ru.top.pass_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.top.pass_system.dto.territoryDTO.TerritoryCreateDTO;
import ru.top.pass_system.dto.territoryDTO.TerritoryResponseDTO;
import ru.top.pass_system.dto.territoryDTO.TerritoryUpdateDTO;
import ru.top.pass_system.service.TerritoryService;

import java.util.List;

@RestController
@RequestMapping("/api/territory")
@RequiredArgsConstructor
public class TerritoryController {

    private final TerritoryService territoryService;

    // Создание новой территории
    @PostMapping
    public ResponseEntity<TerritoryResponseDTO> create(@RequestBody TerritoryCreateDTO territoryCreateDTO) {
        return ResponseEntity.ok(territoryService.create(territoryCreateDTO));
    }

    @GetMapping
    public ResponseEntity<List<TerritoryResponseDTO>> findAll() {
        return ResponseEntity.ok(territoryService.findAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<TerritoryResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(territoryService.findById(id));
    }

    @PutMapping
    public ResponseEntity<TerritoryResponseDTO> update(@RequestBody TerritoryUpdateDTO territoryUpdateDTO) {
        return ResponseEntity.ok(territoryService.update(territoryUpdateDTO));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        territoryService.delete(id);
        return ResponseEntity.ok("Территория с id : " + id + " удалён");
    }
}
