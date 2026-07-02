package com.example.ApointmentManager.model;

import lombok.Getter;

@Getter
public enum Reason {
    CONSULTATION(30),
    CHECK_UP(20),
    OPERATION(120),
    EMERGENCY(60);

    private final long durationMinutes;

    Reason(long durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

}
