package ru.top.pass_system.consts;

public final class RegexConstants {
    public static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{6,20}$";
    public static final String PHONE_REGEX = "^(\\+7|8)\\d{10}$";
    public static final String NAME_REGEX = "^[А-ЯЁ][а-яё]+$";
}
