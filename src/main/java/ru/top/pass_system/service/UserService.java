package ru.top.pass_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.top.pass_system.dto.userDTO.UserCreateDTO;
import ru.top.pass_system.dto.userDTO.UserResponseDTO;
import ru.top.pass_system.model.User;
import ru.top.pass_system.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDTO create(UserCreateDTO userCreateDTO){

       User user = User.builder()
               .firstName(userCreateDTO.getFirstName())
               .lastName(userCreateDTO.getLastName())
               .dateOfBirth(userCreateDTO.getDateOfBirth())
               .phone(userCreateDTO.getPhone())
               .build();

       userRepository.save(user);

        return UserResponseDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .phone(user.getPhone())
                .build();
    }
}
