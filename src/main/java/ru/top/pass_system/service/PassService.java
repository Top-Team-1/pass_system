package ru.top.pass_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.top.pass_system.dto.passDTO.PassCreateDTO;
import ru.top.pass_system.dto.passDTO.PassResponseDTO;
import ru.top.pass_system.model.Pass;
import ru.top.pass_system.model.Territory;
import ru.top.pass_system.model.User;
import ru.top.pass_system.repository.PassRepository;
import ru.top.pass_system.repository.TerritoryRepository;
import ru.top.pass_system.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class PassService {

    private final PassRepository passRepository;
    private final UserRepository userRepository;
    private final TerritoryRepository territoryRepository;

    public PassResponseDTO create(PassCreateDTO passCreateDTO) {

        User user = userRepository.findById(passCreateDTO.getUserId()).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        Territory territory = territoryRepository.findById(passCreateDTO.getTerritoryId()).orElseThrow(() -> new RuntimeException("Территория не найдена"));

        Pass pass = Pass.builder()
                .user(user)
                .territory(territory)
                .isActive(passCreateDTO.isActive())
                .build();

        passRepository.save(pass);

        return PassResponseDTO.builder()
                .id(pass.getId())
                .userId(pass.getUser().getId())
                .territoryId(pass.getTerritory().getId())
                .isActive(pass.isActive())
                .build();
    }
}
