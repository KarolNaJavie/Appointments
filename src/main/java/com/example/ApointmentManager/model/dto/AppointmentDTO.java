package com.example.ApointmentManager.model.dto;

import com.example.ApointmentManager.model.Appointment;
import com.example.ApointmentManager.model.Doctor;
import com.example.ApointmentManager.model.Patient;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AppointmentDTO {

    private Long id;
    private LocalDateTime date;
    private String description;
    private Long durationMinutes;
    private Patient patient;
    private Doctor doctor;

    public static AppointmentDTO fromEntity(Appointment appointment) {
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .date(appointment.getDate())
                .description(appointment.getDescription())
                .durationMinutes(appointment.getDurationMinutes())
                .patient(appointment.getPatient())
                .doctor(appointment.getDoctor())
                .build();
    }
}
