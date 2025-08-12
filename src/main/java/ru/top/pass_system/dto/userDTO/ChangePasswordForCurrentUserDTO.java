package ru.top.pass_system.dto.userDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.top.pass_system.validation.ValidPassword;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordForCurrentUserDTO {
    private String oldPassword;
    @ValidPassword
    private String newPassword;
}
