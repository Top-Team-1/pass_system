package ru.top.pass_system.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.top.pass_system.dto.userDTO.UserCreateDTO;
import ru.top.pass_system.dto.userDTO.UserResponseDTO;
import ru.top.pass_system.dto.userDTO.UserUpdateDTO;
import ru.top.pass_system.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreateDTO userCreateDTO);

    UserResponseDTO toUserResponseDTO(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDTO(UserUpdateDTO userUpdateDTO, @MappingTarget User user);
}
