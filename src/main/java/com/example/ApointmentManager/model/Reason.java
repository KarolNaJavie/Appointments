package com.example.ApointmentManager.model;

public enum Reason {
    CONSULTATION(30),
    CHECK_UP(20),
    OPERATION(120),
    EMERGENCY(60);

    private final long durationMinutes;

    Reason(long durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public long getDurationMinutes() {
        return durationMinutes;
    }
}
