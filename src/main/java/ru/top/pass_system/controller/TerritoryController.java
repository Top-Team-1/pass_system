package ru.top.pass_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.top.pass_system.dto.territoryDTO.TerritoryCreateDTO;
import ru.top.pass_system.dto.territoryDTO.TerritoryFilterDTO;
import ru.top.pass_system.dto.territoryDTO.TerritoryResponseDTO;
import ru.top.pass_system.dto.territoryDTO.TerritoryUpdateDTO;
import ru.top.pass_system.service.TerritoryService;

import java.util.List;

@RestController
@RequestMapping("/api/territory")
@RequiredArgsConstructor
public class TerritoryController {

    private final TerritoryService territoryService;


    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TerritoryResponseDTO> create(@RequestBody TerritoryCreateDTO territoryCreateDTO) {
        return ResponseEntity.ok(territoryService.create(territoryCreateDTO));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<TerritoryResponseDTO>> findAll(@ModelAttribute TerritoryFilterDTO territoryFilterDTO,
                                                              @PageableDefault(size = 5, sort = "id",
                                                                      direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(territoryService.findAll(territoryFilterDTO, pageable));
    }

    @GetMapping(path = "{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TerritoryResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(territoryService.findById(id));
    }

    @PutMapping
    public ResponseEntity<TerritoryResponseDTO> update(@RequestBody TerritoryUpdateDTO territoryUpdateDTO) {
        return ResponseEntity.ok(territoryService.update(territoryUpdateDTO));
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        territoryService.delete(id);
        return ResponseEntity.ok("Территория с id : " + id + " удалён");
    }

    @GetMapping(path = "my")
    public ResponseEntity<List<TerritoryResponseDTO>> getUserWithZone(){

        return ResponseEntity.ok(territoryService.getTerritoryForCurrentUser());
    }
}
