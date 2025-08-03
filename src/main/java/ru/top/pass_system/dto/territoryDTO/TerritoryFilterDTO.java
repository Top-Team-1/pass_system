package ru.top.pass_system.dto.territoryDTO;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.top.pass_system.enums.TerritoryType;

import java.time.LocalDateTime;

@Data
@Builder
public class TerritoryFilterDTO {
    private String name;
    private String address;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime addedAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
    private TerritoryType type;
}
