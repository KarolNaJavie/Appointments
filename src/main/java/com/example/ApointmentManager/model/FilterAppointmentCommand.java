package com.example.ApointmentManager.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FilterAppointmentCommand {

    private Long doctorId;
    private Long patientId;
    private Reason reason;
    private LocalDateTime from;
    private LocalDateTime to;
}
