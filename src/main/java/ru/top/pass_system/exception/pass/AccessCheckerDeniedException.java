package ru.top.pass_system.exception.pass;

public class AccessCheckerDeniedException extends RuntimeException {
    private static final String MESSAGE = "У вас нет прав для совершения этого действия";

    public AccessCheckerDeniedException() {
        super(MESSAGE);
    }
}
