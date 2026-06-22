package com.example.ApointmentManager.repository;

import com.example.ApointmentManager.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByDoctorIdAndDateBetween(Long doctorId, LocalDateTime date, LocalDateTime localDateTime);

    boolean existsByPatientIdAndDateBetween(Long patientId, LocalDateTime date, LocalDateTime localDateTime);
}
