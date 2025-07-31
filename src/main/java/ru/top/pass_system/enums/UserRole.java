package ru.top.pass_system.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
public enum UserRole {

    USER,
    SECURITY,
    ADMIN
}
