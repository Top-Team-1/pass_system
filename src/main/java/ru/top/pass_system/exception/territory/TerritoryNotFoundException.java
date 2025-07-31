package ru.top.pass_system.exception.territory;

public class TerritoryNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Территория с id %d не найдена";

    public TerritoryNotFoundException(Long id) {
        super(MESSAGE.formatted(id));
    }
}
