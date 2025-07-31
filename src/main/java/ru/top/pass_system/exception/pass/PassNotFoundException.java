package ru.top.pass_system.exception.pass;

public class PassNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Пропуск с id %d не найден";

    public PassNotFoundException(Long id) {
        super(MESSAGE.formatted(id));
    }
}
