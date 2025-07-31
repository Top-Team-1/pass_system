package ru.top.pass_system.dto.territoryDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.top.pass_system.enums.TerritoryType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class TerritoryCreateDTO {

    private String name;
    private String address;
    private TerritoryType type;
}
