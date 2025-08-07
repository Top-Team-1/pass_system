package ru.top.pass_system.mapper;

import org.mapstruct.Mapper;
import ru.top.pass_system.dto.territoryDTO.UserTerritoryCreateDTO;
import ru.top.pass_system.model.UserTerritory;

@Mapper(componentModel = "spring")
public interface UserTerritoryMapper {
    UserTerritory toUserTerritory (UserTerritoryCreateDTO userTerritoryCreateDTO);
}
