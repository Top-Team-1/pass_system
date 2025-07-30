package ru.top.pass_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.top.pass_system.dto.passDTO.PassCreateDTO;
import ru.top.pass_system.dto.passDTO.PassResponseDTO;
import ru.top.pass_system.dto.passDTO.PassUpdateDTO;
import ru.top.pass_system.exception.PassNotFoundException;
import ru.top.pass_system.mapper.PassMapper;
import ru.top.pass_system.model.Pass;
import ru.top.pass_system.model.Territory;
import ru.top.pass_system.model.User;
import ru.top.pass_system.repository.PassRepository;
import ru.top.pass_system.repository.TerritoryRepository;
import ru.top.pass_system.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassService {

    private final PassRepository passRepository;
    private final UserRepository userRepository;
    private final TerritoryRepository territoryRepository;
    private final PassMapper passMapper;

    public PassResponseDTO create(PassCreateDTO passCreateDTO) {
        User user = userRepository.findById(passCreateDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        Territory territory = territoryRepository.findById(passCreateDTO.getTerritoryId())
                .orElseThrow(() -> new RuntimeException("Территория не найдена"));


        Pass pass = passMapper.toPass(passCreateDTO);

        return passMapper.toPassResponseDTO(passRepository.save(pass));
    }

    public List<PassResponseDTO> findAll() {
        return passRepository.findAll().stream()
                .map(passMapper::toPassResponseDTO)
                .toList();
    }

    public PassResponseDTO findById(Long id) {
        Pass pass = passRepository.findById(id)
                .orElseThrow(() -> new PassNotFoundException(id));

        return passMapper.toPassResponseDTO(pass);
    }

    @Transactional
    public PassResponseDTO update(PassUpdateDTO passUpdateDTO) {
        Pass pass = passRepository.findById(passUpdateDTO.getId())
                .orElseThrow(() -> new PassNotFoundException(passUpdateDTO.getId()));

        passMapper.updatePassFromDTO(passUpdateDTO, pass);

        return passMapper.toPassResponseDTO(passRepository.save(pass));
    }

    public void delete(Long id) {
        Pass pass = passRepository.findById(id)
                .orElseThrow(() -> new PassNotFoundException(id));

        passRepository.delete(pass);
    }
}
