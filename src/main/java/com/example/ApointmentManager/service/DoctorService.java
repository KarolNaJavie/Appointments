package com.example.ApointmentManager.service;

import com.example.ApointmentManager.model.dto.DoctorDTO;
import com.example.ApointmentManager.repository.DoctorRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class DoctorService {
    public final DoctorRepository doctorRepository;

    public List<DoctorDTO> findAll() {
        return doctorRepository.findAll()
                .stream()
                .map(DoctorDTO::fromEntity)
                .toList();
    }
}
