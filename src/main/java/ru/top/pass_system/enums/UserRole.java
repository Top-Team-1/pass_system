package ru.top.pass_system.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRole {

    USER,
    SECURITY,
    ADMIN
}
