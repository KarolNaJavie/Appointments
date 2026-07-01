package com.example.ApointmentManager.service;

import com.example.ApointmentManager.model.dto.PatientDTO;
import com.example.ApointmentManager.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PatientService {
    public final PatientRepository patientRepository;

    public List<PatientDTO> findAll() {
        return patientRepository.findAll()
                .stream()
                .map(PatientDTO::fromEntity).toList();
    }
}
