package ru.top.pass_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.top.pass_system.dto.territoryDTO.TerritoryCreateDTO;
import ru.top.pass_system.dto.territoryDTO.TerritoryResponseDTO;
import ru.top.pass_system.model.Territory;
import ru.top.pass_system.repository.TerritoryRepository;

@Service
@RequiredArgsConstructor
public class TerritoryService {

    private final TerritoryRepository territoryRepository;

    public TerritoryResponseDTO create(TerritoryCreateDTO territoryCreateDTO) {

        Territory territory = Territory.builder()
                .name(territoryCreateDTO.getName())
                .description(territoryCreateDTO.getDescription())
                .build();

        territoryRepository.save(territory);

        return TerritoryResponseDTO.builder()
                .id(territory.getId())
                .name(territory.getName())
                .description(territory.getDescription())
                .build();
    }
}
