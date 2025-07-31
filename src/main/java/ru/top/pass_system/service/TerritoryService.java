package ru.top.pass_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.top.pass_system.dto.territoryDTO.TerritoryCreateDTO;
import ru.top.pass_system.dto.territoryDTO.TerritoryResponseDTO;
import ru.top.pass_system.dto.territoryDTO.TerritoryUpdateDTO;
import ru.top.pass_system.exception.territory.TerritoryAlreadyExistsException;
import ru.top.pass_system.exception.territory.TerritoryNotFoundException;
import ru.top.pass_system.mapper.TerritoryMapper;
import ru.top.pass_system.model.Territory;
import ru.top.pass_system.repository.TerritoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TerritoryService {

    private final TerritoryRepository territoryRepository;
    private final TerritoryMapper territoryMapper;

    public TerritoryResponseDTO create(TerritoryCreateDTO territoryCreateDTO) {

        if (territoryRepository.existsByAddress(territoryCreateDTO.getAddress())) {
            throw new TerritoryAlreadyExistsException(territoryCreateDTO.getAddress());
        }
        Territory territory = territoryMapper.toTerritory(territoryCreateDTO);

        return territoryMapper.toTerritoryResponseDTO(territoryRepository.save(territory));
    }

    public List<TerritoryResponseDTO> findAll() {
        return territoryRepository.findAll().stream()
                .map(territoryMapper::toTerritoryResponseDTO)
                .toList();
    }

    public TerritoryResponseDTO findById(Long id) {
        Territory territory = territoryRepository.findById(id)
                .orElseThrow(() -> new TerritoryNotFoundException(id));
        return territoryMapper.toTerritoryResponseDTO(territory);
    }

    @Transactional
    public TerritoryResponseDTO update(TerritoryUpdateDTO territoryUpdateDTO) {
        Territory territory = territoryRepository.findById(territoryUpdateDTO.getId())
                .orElseThrow(() -> new TerritoryNotFoundException(territoryUpdateDTO.getId()));
        territoryMapper.updateTerritoryFromDTO(territoryUpdateDTO, territory);
        return territoryMapper.toTerritoryResponseDTO(territoryRepository.save(territory));
    }

    public void delete(Long id) {
        Territory territory = territoryRepository.findById(id)
                .orElseThrow(() -> new TerritoryNotFoundException(id));
        territoryRepository.delete(territory);
    }
}
