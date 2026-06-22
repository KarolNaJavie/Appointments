package com.example.ApointmentManager.model.common.exception;

public class DateAlreadyTaken extends RuntimeException {
    public DateAlreadyTaken(String message) {
        super(message);
    }
}
