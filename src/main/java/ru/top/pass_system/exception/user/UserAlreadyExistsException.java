package ru.top.pass_system.exception.user;

public class UserAlreadyExistsException extends RuntimeException {

    private static final String MESSAGE = "Пользователь с номером телефона %s уже существует!";

    public UserAlreadyExistsException(String phone) {
        super(MESSAGE.formatted(phone));
    }
}
