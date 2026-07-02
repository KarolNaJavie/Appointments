package com.example.ApointmentManager.Appointment;

import com.example.ApointmentManager.model.*;
import com.example.ApointmentManager.model.common.exception.DateAlreadyTaken;
import com.example.ApointmentManager.model.dto.AppointmentDTO;
import com.example.ApointmentManager.repository.AppointmentRepository;
import com.example.ApointmentManager.repository.DoctorRepository;
import com.example.ApointmentManager.repository.PatientRepository;
import com.example.ApointmentManager.service.AppointmentService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private DoctorRepository doctorRepository;
    @Mock
    private PatientRepository patientRepository;

    @Test
    void testFindAll_HappyPath() {
        Doctor doctor = Doctor.builder().id(1L).build();
        Patient patient = Patient.builder().id(1L).build();
        Appointment appointment = Appointment.builder().id(1L).doctor(doctor).patient(patient).build();
        when(appointmentRepository.findAll()).thenReturn(List.of(appointment));

        List<AppointmentDTO> saved = appointmentService.findAll();

        verify(appointmentRepository).findAll();
        assertThat(saved).hasSize(1);
        assertEquals(saved.getFirst().getId(), appointment.getId());
    }

    @Test
    public void testCreate_ResultsInDoctorNotFound() {
        CreateAppointmentCommand command = new CreateAppointmentCommand();
        command.setDoctorId(1L);
        command.setReason(Reason.CHECK_UP);
        command.setDate(LocalDateTime.now().plusDays(1));
        when(doctorRepository.findById(any())).thenReturn(Optional.empty());
        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> appointmentService.create(command));
    }

    @Test
    public void testCreate_ResultsInPatientNotFound() {
        CreateAppointmentCommand command = new CreateAppointmentCommand();
        command.setDoctorId(1L);
        command.setPatientId(1L);
        command.setReason(Reason.CHECK_UP);
        command.setDate(LocalDateTime.now().plusDays(1));
        when(doctorRepository.findById(any())).thenReturn(Optional.of(Doctor.builder().build()));
        when(patientRepository.findById(any())).thenReturn(Optional.empty());
        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> appointmentService.create(command));
    }

    @Test
    public void testValidateAppointment_ResultsInDoctorAlreadyHasAppoinment() {
        CreateAppointmentCommand command = new CreateAppointmentCommand();
        command.setDoctorId(1L);
        command.setPatientId(1L);
        command.setReason(Reason.CHECK_UP);
        command.setDate(LocalDateTime.now().plusDays(1));
        when(appointmentRepository.existsByDoctorIdAndDateBetween(command.getDoctorId(),
                command.getDate(), command.getDate().plusMinutes(command.getReason().getDurationMinutes()))).thenReturn(true);
        assertThatExceptionOfType(DateAlreadyTaken.class).isThrownBy(() -> appointmentService.validateAppointment(command));
    }

    @Test
    public void testValidateAppointment_ResultsInPatientAlreadyHasAppointment() {
        CreateAppointmentCommand command = new CreateAppointmentCommand();
        command.setDoctorId(1L);
        command.setPatientId(1L);
        command.setReason(Reason.CHECK_UP);
        command.setDate(LocalDateTime.now().plusDays(1));
        when(appointmentRepository.existsByPatientIdAndDateBetween(command.getPatientId(),
                command.getDate(), command.getDate().plusMinutes(command.getReason().getDurationMinutes()))).thenReturn(true);
        assertThatExceptionOfType(DateAlreadyTaken.class).isThrownBy(() -> appointmentService.validateAppointment(command));
    }

    @Test
    public void testFilter_ResultsInDoctorBeingFound() {
        Doctor doctor1 = Doctor.builder().id(1L).build();
        Doctor doctor2 = Doctor.builder().id(2L).build();
        Patient patient1 = Patient.builder().id(1L).build();
        Patient patient2 = Patient.builder().id(2L).build();

        Appointment appointment1 = Appointment.builder()
                .id(1L).doctor(doctor1).patient(patient1).build();
        Appointment appointment2 = Appointment.builder()
                .id(2L).doctor(doctor2).patient(patient2).build();

        FilterAppointmentCommand filterAppointmentCommand = new FilterAppointmentCommand();
        filterAppointmentCommand.setDoctorId(2L);

        when(appointmentRepository.findAll(any((Sort.class)))).thenReturn(List.of(appointment1, appointment2));
        List<AppointmentDTO> saved = appointmentService.findFiltered(filterAppointmentCommand);

        assertThat(saved).hasSize(1);
        assertEquals(saved.getFirst().getDoctor().getId(), doctor2.getId());
    }

    @Test
    public void testFilter_ResultsInReasonBeingFound() {
        Doctor doctor1 = Doctor.builder().id(1L).build();
        Doctor doctor2 = Doctor.builder().id(2L).build();
        Patient patient1 = Patient.builder().id(1L).build();
        Patient patient2 = Patient.builder().id(2L).build();

        Appointment appointment1 = Appointment.builder()
                .id(1L).doctor(doctor1).patient(patient1).reason(Reason.CHECK_UP).build();
        Appointment appointment2 = Appointment.builder()
                .id(2L).doctor(doctor2).patient(patient2).reason(Reason.OPERATION).build();

        FilterAppointmentCommand filterAppointmentCommand = new FilterAppointmentCommand();
        filterAppointmentCommand.setReason(Reason.OPERATION);

        when(appointmentRepository.findAll(any((Sort.class)))).thenReturn(List.of(appointment1, appointment2));
        List<AppointmentDTO> saved = appointmentService.findFiltered(filterAppointmentCommand);

        assertThat(saved).hasSize(1);
        assertEquals(Reason.OPERATION, saved.getFirst().getReason());

    }
}
