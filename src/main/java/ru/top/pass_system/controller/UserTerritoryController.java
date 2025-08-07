package ru.top.pass_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.top.pass_system.dto.territoryDTO.UserTerritoryCreateDTO;
import ru.top.pass_system.service.UserTerritoryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user_territory")
public class UserTerritoryController {
    private final UserTerritoryService userTerritoryService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody UserTerritoryCreateDTO userTerritoryCreateDTO) {
        userTerritoryService.create(userTerritoryCreateDTO);
        return ResponseEntity.ok("Юзеру добавлена территория");
    }
}
