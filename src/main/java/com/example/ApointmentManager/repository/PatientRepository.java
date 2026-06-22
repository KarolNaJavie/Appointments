package com.example.ApointmentManager.repository;

import com.example.ApointmentManager.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
