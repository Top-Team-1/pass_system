package ru.top.pass_system.exception.user;

public class InvalidPasswordException extends RuntimeException {
    private static final String MESSAGE = "Неверный пароль";
    public InvalidPasswordException() {
        super(MESSAGE);
    }
}
