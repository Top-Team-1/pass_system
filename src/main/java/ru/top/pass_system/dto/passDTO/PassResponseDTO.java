package ru.top.pass_system.dto.passDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PassResponseDTO {

    private Long id;
    private Long userId;
    private Long territoryId;
    private Boolean isActive;
}
