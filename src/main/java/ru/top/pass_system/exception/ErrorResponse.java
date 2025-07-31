package ru.top.pass_system.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ErrorResponse {

    private LocalDateTime time;
    private int status;
    private String error;
    private String message;

}
