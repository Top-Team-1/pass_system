package ru.top.pass_system.mapper;

import org.mapstruct.*;
import ru.top.pass_system.dto.passDTO.PassCreateDTO;
import ru.top.pass_system.dto.passDTO.PassResponseDTO;
import ru.top.pass_system.dto.passDTO.PassUpdateDTO;
import ru.top.pass_system.model.Pass;

@Mapper(componentModel = "spring")
public interface PassMapper {

    Pass toPass(PassCreateDTO passCreateDTO);

    @Mapping(target = "territoryName", source = "territory.name")
    PassResponseDTO toPassResponseDTO(Pass pass);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePassFromDTO(PassUpdateDTO passUpdateDTO, @MappingTarget Pass pass);
}
