package ru.top.pass_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.top.pass_system.dto.passDTO.PassCreateDTO;
import ru.top.pass_system.dto.passDTO.PassFilterDTO;
import ru.top.pass_system.dto.passDTO.PassResponseDTO;
import ru.top.pass_system.dto.passDTO.PassUpdateDTO;
import ru.top.pass_system.enums.PassStatus;
import ru.top.pass_system.exception.pass.PassNotFoundException;
import ru.top.pass_system.exception.territory.TerritoryNotFoundException;
import ru.top.pass_system.mapper.PassMapper;
import ru.top.pass_system.model.Pass;
import ru.top.pass_system.model.Territory;
import ru.top.pass_system.model.User;
import ru.top.pass_system.repository.PassRepository;
import ru.top.pass_system.repository.TerritoryRepository;
import ru.top.pass_system.specification.PassSpecification;



@Service
@RequiredArgsConstructor
public class PassService {

    private final PassRepository passRepository;
    private final TerritoryRepository territoryRepository;
    private final PassMapper passMapper;
    private final CurrentUserService currentUserService;
    private final AccessChecker accessChecker;

    /**
     * Создает новый пропуск и сохраняет его в базу данных
     *
     * @param passCreateDTO данные для создания пропуска
     * @return сохраненный пропуск
     */
    @Transactional
    public PassResponseDTO create(PassCreateDTO passCreateDTO) {

        User user  = currentUserService.findUser();


       Territory territory = territoryRepository.findById(passCreateDTO.getTerritoryId())
               .orElseThrow(() -> new TerritoryNotFoundException(passCreateDTO.getTerritoryId()));

       accessChecker.checkUserTerritoryOwnership(user, territory);

        Pass pass = passMapper.toPass(passCreateDTO);
        pass.setUser(user);
        pass.setTerritory(territory);
        pass.setStatus(PassStatus.ACTIVE); // TODO ПЕРЕДЕЛАТЬ! Написать метод, который будет проверять даты и исходя из этого ставится статус

        return passMapper.toPassResponseDTO(passRepository.save(pass));
    }

    /**
     * Находит список пропусков с возможностью фильтрации и постраничного вывода
     *
     * @param filter   условия фильтрации
     * @param pageable настройки страницы
     * @return страница пропусков
     */
    public Page<PassResponseDTO> findAll(PassFilterDTO filter, Pageable pageable) {
        Specification<Pass> specification = PassSpecification.createSpecification(filter);
        return passRepository.findAll(specification, pageable).map(passMapper::toPassResponseDTO);
    }

    /**
     * Находит пропуск по указанному идентификатору
     *
     * @param id идентификатор пропуска
     * @return найденный пропуск
     */
    public PassResponseDTO findById(Long id) {
        Pass pass = passRepository.findById(id)
                .orElseThrow(() -> new PassNotFoundException(id));
        return passMapper.toPassResponseDTO(pass);
    }

    /**
     * Обновляет существующие данные пропуска
     *
     * @param passUpdateDTO данные для обновления пропуска
     * @return обновленный пропуск
     */
    @Transactional
    public PassResponseDTO update(PassUpdateDTO passUpdateDTO) {
        Pass pass = passRepository.findById(passUpdateDTO.getId())
                .orElseThrow(() -> new PassNotFoundException(passUpdateDTO.getId()));

        passMapper.updatePassFromDTO(passUpdateDTO, pass);
        return passMapper.toPassResponseDTO(passRepository.save(pass));
    }

    /**
     * Удаляет пропуск по идентификатору
     *
     * @param id идентификатор пропуска
     */
    public void delete(Long id) {
        Pass pass = passRepository.findById(id)
                .orElseThrow(() -> new PassNotFoundException(id));
        passRepository.delete(pass);
    }

    public Page<PassResponseDTO> getPassesForCurrentUser(PassFilterDTO passFilterDTO, Pageable pageable){

        User user = currentUserService.findUser();

        passFilterDTO.setUserId(user.getId());

        return findAll(passFilterDTO, pageable);
    }

}
