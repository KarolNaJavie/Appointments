package com.example.ApointmentManager.model.dto;

import com.example.ApointmentManager.model.Appointment;
import com.example.ApointmentManager.model.Reason;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AppointmentDTO {

    private Long id;
    private LocalDateTime date;
    private Reason reason;
    private PatientDTO patient;
    private DoctorDTO doctor;

    public static AppointmentDTO fromEntity(Appointment appointment) {
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .date(appointment.getDate())
                .reason(appointment.getReason())
                .patient(PatientDTO.fromEntity(appointment.getPatient()))
                .doctor(DoctorDTO.fromEntity(appointment.getDoctor()))
                .build();
    }
}
