package ru.top.pass_system.dto.passDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PassResponseDTO {

    private Long id;
    private UserDTO user;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String type;
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime addedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updatedAt;

    private String territoryName;
    private String firstName;
    private String lastName;

    @Getter
    @AllArgsConstructor
    @Setter
    @NoArgsConstructor
    public static class UserDTO {

        private String firstName;
        private String lastName;
        private String phone;
    }
}
