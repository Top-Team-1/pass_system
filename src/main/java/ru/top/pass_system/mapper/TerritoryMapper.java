package ru.top.pass_system.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.top.pass_system.dto.territoryDTO.TerritoryCreateDTO;
import ru.top.pass_system.dto.territoryDTO.TerritoryResponseDTO;
import ru.top.pass_system.dto.territoryDTO.TerritoryUpdateDTO;
import ru.top.pass_system.model.Territory;

@Mapper(componentModel = "spring")
public interface TerritoryMapper {
    Territory toTerritory(TerritoryCreateDTO territoryCreateDTO);

    TerritoryResponseDTO toTerritoryResponseDTO(Territory territory);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTerritoryFromDTO(TerritoryUpdateDTO territoryUpdateDTO, @MappingTarget Territory territory);
}
