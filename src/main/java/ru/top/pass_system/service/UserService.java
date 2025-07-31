package ru.top.pass_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.top.pass_system.dto.userDTO.UserCreateDTO;
import ru.top.pass_system.dto.userDTO.UserResponseDTO;
import ru.top.pass_system.dto.userDTO.UserUpdateDTO;
import ru.top.pass_system.enums.UserRole;
import ru.top.pass_system.exception.user.UserAlreadyExistsException;
import ru.top.pass_system.exception.user.UserNotFoundException;
import ru.top.pass_system.mapper.UserMapper;
import ru.top.pass_system.model.User;
import ru.top.pass_system.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDTO create(UserCreateDTO userCreateDTO){

        if(userRepository.existsByPhone(userCreateDTO.getPhone())){
            throw new UserAlreadyExistsException(userCreateDTO.getPhone());
        }

        User user =  userMapper.toUser(userCreateDTO);

        user.setRole(UserRole.USER);

        return userMapper.toUserResponseDTO(userRepository.save(user));
    }

    public List<UserResponseDTO> findAll(){

        return userRepository.findAll().stream()
                .map(userMapper::toUserResponseDTO)
                .toList();

    }

    public UserResponseDTO findById(Long id){

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return userMapper.toUserResponseDTO(user);
    }

    @Transactional
    public UserResponseDTO update(UserUpdateDTO userUpdateDTO){

        User user = userRepository.findById(userUpdateDTO.getId())
                .orElseThrow(() -> new UserNotFoundException(userUpdateDTO.getId()));

        if(userRepository.existsByPhone(userUpdateDTO.getPhone())){
            throw new UserAlreadyExistsException(userUpdateDTO.getPhone());
        }

        userMapper.updateUserFromDTO(userUpdateDTO, user);

        return userMapper.toUserResponseDTO(userRepository.save(user));

    }

    public void delete(Long id){

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepository.delete(user);
    }
}
