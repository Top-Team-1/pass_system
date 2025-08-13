package ru.top.pass_system.dto.userDTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.top.pass_system.validation.ValidName;
import ru.top.pass_system.validation.ValidPassword;
import ru.top.pass_system.validation.ValidPhone;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SignUpRequest {

    @ValidName
    private String firstName;

    @ValidName
    private String lastName;

    @Past
    private LocalDate dateOfBirth;

    @ValidPhone
    private String phone;

    @ValidPassword
    private String password;
}
