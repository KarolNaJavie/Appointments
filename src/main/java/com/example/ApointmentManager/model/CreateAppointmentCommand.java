package com.example.ApointmentManager.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class CreateAppointmentCommand {
    private LocalDateTime date;
    private String description;
    private Long durationMinutes;
    private Long patientId;
    private Long doctorId;
}
