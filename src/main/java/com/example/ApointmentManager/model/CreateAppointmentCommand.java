package com.example.ApointmentManager.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class CreateAppointmentCommand {
    private LocalDateTime date;
    private Reason reason;
    private Long patientId;
    private Long doctorId;
}
