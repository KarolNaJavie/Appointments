package com.example.ApointmentManager.model.dto;

import com.example.ApointmentManager.model.Doctor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DoctorDTO {
    public Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String specialization;

    public static DoctorDTO fromEntity(Doctor doctor) {
        return DoctorDTO.builder()
                .id(doctor.getId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .birthDate(doctor.getBirthDate())
                .specialization(doctor.getSpecialization())
                .build();
    }
}
