package ru.top.pass_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.top.pass_system.dto.territoryDTO.TerritoryCreateDTO;
import ru.top.pass_system.dto.territoryDTO.TerritoryResponseDTO;
import ru.top.pass_system.service.TerritoryService;

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
}
