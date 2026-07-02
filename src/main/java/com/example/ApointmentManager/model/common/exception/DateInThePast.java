package com.example.ApointmentManager.model.common.exception;

public class DateInThePast extends RuntimeException {
    public DateInThePast(String message) {
        super(message);
    }
}
