package ru.top.pass_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.top.pass_system.dto.userDTO.SignUpRequest;
import ru.top.pass_system.dto.userDTO.UserFilterDTO;
import ru.top.pass_system.dto.userDTO.UserResponseDTO;
import ru.top.pass_system.dto.userDTO.UserUpdateDTO;
import ru.top.pass_system.enums.UserRole;
import ru.top.pass_system.exception.user.UserAlreadyExistsException;
import ru.top.pass_system.exception.user.UserNotFoundException;
import ru.top.pass_system.mapper.UserMapper;
import ru.top.pass_system.model.User;
import ru.top.pass_system.repository.UserRepository;
import ru.top.pass_system.specification.UserSpecification;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public User signUp(User user) {

        if (userRepository.existsByPhone(user.getPhone())) {
            throw new UserAlreadyExistsException(user.getPhone());
        }

        user.setRole(UserRole.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public Page<UserResponseDTO> findAll(UserFilterDTO filter, Pageable pageable) {

        Specification<User> spec = UserSpecification.createSpecification(filter);

        return userRepository.findAll(spec, pageable).map(userMapper::toUserResponseDTO);

    }

    public UserResponseDTO findById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return userMapper.toUserResponseDTO(user);
    }

    @Transactional
    public UserResponseDTO update(UserUpdateDTO userUpdateDTO) {

        User user = userRepository.findById(userUpdateDTO.getId())
                .orElseThrow(() -> new UserNotFoundException(userUpdateDTO.getId()));

        if (userRepository.existsByPhone(userUpdateDTO.getPhone())) {
            throw new UserAlreadyExistsException(userUpdateDTO.getPhone());
        }

        userMapper.updateUserFromDTO(userUpdateDTO, user);

        return userMapper.toUserResponseDTO(userRepository.save(user));

    }

    public void delete(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepository.delete(user);
    }


    public User getByUsername(String phone) {
        return userRepository.findByPhone(phone)
                .orElseThrow(() -> new UserNotFoundException(phone));
    }


    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }


    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    public UserResponseDTO create(SignUpRequest signUpRequest){

        User user = userMapper.toUser(signUpRequest);

        return userMapper.toUserResponseDTO(signUp(user));
    }
}
