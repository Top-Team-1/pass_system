package ru.top.pass_system.exception;

public class UserNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Пользователь с id %d не найден";

    public UserNotFoundException(Long id) {
        super(MESSAGE.formatted(id));
    }
}
