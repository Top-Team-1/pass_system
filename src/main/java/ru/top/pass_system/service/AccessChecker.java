package ru.top.pass_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.top.pass_system.enums.UserRole;
import ru.top.pass_system.exception.pass.AccessCheckerDeniedException;
import ru.top.pass_system.model.Territory;
import ru.top.pass_system.model.User;
import ru.top.pass_system.repository.UserTerritoryRepository;

@Service
@RequiredArgsConstructor
public class AccessChecker {

    private final UserTerritoryRepository userTerritoryRepository;

    public void checkUserTerritoryOwnership(User user, Territory territory){

        if(user.getRole() == UserRole.ADMIN){
            return;
        }

        if(!userTerritoryRepository.existsByUserIdAndTerritoryId(user.getId(), territory.getId())){

            throw new AccessCheckerDeniedException();
        }
    }
}
