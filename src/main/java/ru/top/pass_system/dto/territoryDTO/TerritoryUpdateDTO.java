package ru.top.pass_system.dto.territoryDTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class TerritoryUpdateDTO {
    private Long id;
    private String name;
    private String address;
}
