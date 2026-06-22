package com.example.ApointmentManager.service;

import com.example.ApointmentManager.model.dto.PatientDTO;
import com.example.ApointmentManager.repository.PatientRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class PatientService {
    public final PatientRepository patientRepository;

    public List<PatientDTO> findAll() {
        return patientRepository.findAll()
                .stream()
                .map(PatientDTO::fromEntity).toList();
    }
}
