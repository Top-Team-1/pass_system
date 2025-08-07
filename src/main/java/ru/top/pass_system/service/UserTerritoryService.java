package ru.top.pass_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.top.pass_system.dto.territoryDTO.UserTerritoryCreateDTO;
import ru.top.pass_system.exception.territory.TerritoryNotFoundException;
import ru.top.pass_system.exception.user.UserNotFoundException;
import ru.top.pass_system.mapper.UserTerritoryMapper;
import ru.top.pass_system.model.Territory;
import ru.top.pass_system.model.User;
import ru.top.pass_system.model.UserTerritory;
import ru.top.pass_system.repository.TerritoryRepository;
import ru.top.pass_system.repository.UserRepository;
import ru.top.pass_system.repository.UserTerritoryRepository;

@Service
@RequiredArgsConstructor

public class UserTerritoryService {
    private final UserTerritoryRepository userTerritoryRepository;
    private final TerritoryRepository territoryRepository;
    private final UserRepository userRepository;
    private final UserTerritoryMapper userTerritoryMapper;

    public void create(UserTerritoryCreateDTO userTerritoryCreateDTO) {
        User user = userRepository.findById(userTerritoryCreateDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(userTerritoryCreateDTO.getUserId()));
        Territory territory = territoryRepository.findById((userTerritoryCreateDTO.getTerritoryId()))
                .orElseThrow(() -> new TerritoryNotFoundException((userTerritoryCreateDTO.getTerritoryId())));

        UserTerritory userTerritory = userTerritoryMapper.toUserTerritory(userTerritoryCreateDTO);
        userTerritory.setTerritory(territory);
        userTerritory.setUser(user);
        userTerritoryRepository.save(userTerritory);
    }
}
