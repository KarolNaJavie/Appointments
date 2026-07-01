package com.example.ApointmentManager.service;

import com.example.ApointmentManager.model.*;
import com.example.ApointmentManager.model.common.exception.DateAlreadyTaken;
import com.example.ApointmentManager.model.common.exception.DateInThePastException;
import com.example.ApointmentManager.model.dto.AppointmentDTO;
import com.example.ApointmentManager.repository.AppointmentRepository;
import com.example.ApointmentManager.repository.DoctorRepository;
import com.example.ApointmentManager.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public AppointmentDTO create(CreateAppointmentCommand cmd) {
        validateAppointment(cmd);
        Doctor doctor = doctorRepository.findById(cmd.getDoctorId()).orElseThrow(EntityNotFoundException::new);
        Patient patient = patientRepository.findById(cmd.getPatientId()).orElseThrow(EntityNotFoundException::new);
        return AppointmentDTO.fromEntity(appointmentRepository.save(Appointment
                .builder()
                .date(cmd.getDate())
                .doctor(doctor)
                .patient(patient)
                .reason(cmd.getReason())
                .build()));
    }

    public List<AppointmentDTO> findAll() {
        return appointmentRepository.findAll()
                .stream()
                .map(AppointmentDTO::fromEntity)
                .toList();
    }

    public List<AppointmentDTO> findFiltered(FilterAppointmentCommand f) {
        return appointmentRepository.findAll().stream()
                .filter(a -> f.getDoctorId() == null ||
                        a.getDoctor().getId().equals(f.getDoctorId()))
                .filter(a -> f.getPatientId() == null ||
                        a.getPatient().getId().equals(f.getPatientId()))
                .filter(a -> f.getReason() == null ||
                        a.getReason().equals(f.getReason()))
                .filter(a -> f.getFrom() == null ||
                        !a.getDate().isBefore(f.getFrom()))
                .filter(a -> f.getTo() == null ||
                        !a.getDate().isAfter(f.getTo()))
                .map(AppointmentDTO::fromEntity)
                .toList();
    }

    public void validateAppointment(CreateAppointmentCommand command) {
        if (appointmentRepository.existsByDoctorIdAndDateBetween(command.getDoctorId(),
                command.getDate(), command.getDate().plusMinutes(command.getReason().getDurationMinutes()))) {
            throw new DateAlreadyTaken("Doctor already has appointment with that date!");
        }
        if (appointmentRepository.existsByPatientIdAndDateBetween(command.getPatientId(),
                command.getDate(), command.getDate().plusMinutes(command.getReason().getDurationMinutes()))) {
            throw new DateAlreadyTaken("Patient already has appointment with that date!");
        }
        if (!command.getDate().isAfter(LocalDateTime.now())) {
            throw new DateInThePastException("Date cannot be in the past!");
        }
    }
}
