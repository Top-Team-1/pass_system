package ru.top.pass_system.dto.userDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SignUpRequest {

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phone;
    private String password;
}
