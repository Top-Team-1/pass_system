package ru.top.pass_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.top.pass_system.dto.userDTO.ChangePasswordForCurrentUserDTO;
import ru.top.pass_system.dto.userDTO.CurrentUserUpdateDTO;
import ru.top.pass_system.dto.userDTO.UserResponseDTO;
import ru.top.pass_system.exception.user.InvalidPasswordException;
import ru.top.pass_system.exception.user.UserAlreadyExistsException;
import ru.top.pass_system.exception.user.UserNotFoundException;
import ru.top.pass_system.mapper.UserMapper;
import ru.top.pass_system.model.User;
import ru.top.pass_system.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public User findUser() {

        return userRepository.findById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId())
                .orElseThrow(() -> new UserNotFoundException(findUser().getId()));

    }

    public UserResponseDTO getCurrentUser() {

        User currentUser = findUser();

        return userMapper.toUserResponseDTO(currentUser);
    }

    @Transactional
    public UserResponseDTO update(CurrentUserUpdateDTO currentUserUpdateDTO) {

        User currentUser = findUser();

        if (userRepository.existsByPhone(currentUserUpdateDTO.getPhone())) {
            throw new UserAlreadyExistsException(currentUserUpdateDTO.getPhone());
        }

        userMapper.updateUserFromDTO(currentUserUpdateDTO, currentUser);

        return userMapper.toUserResponseDTO(userRepository.save(currentUser));
    }

    public User getUserWithZone(){

        return userRepository.getUserWithZone(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId())
                .orElseThrow(() -> new UserNotFoundException(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));
    }

    @Transactional
    public void changePassword(ChangePasswordForCurrentUserDTO changePasswordForCurrentUserDTO) {
        User user = findUser();
        if (!passwordEncoder.matches(changePasswordForCurrentUserDTO.getOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException();
        }
        user.setPassword(passwordEncoder.encode(changePasswordForCurrentUserDTO.getNewPassword()));
        userRepository.save(user);
    }
}
