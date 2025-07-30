package ru.top.pass_system.exception;

public class TerritoryAlreadyExistsException extends RuntimeException {

    private static final String MESSAGE = "Территория с таким адресом %s уже существует";

    public TerritoryAlreadyExistsException(String address) {
        super(MESSAGE.formatted(address));
    }
}
