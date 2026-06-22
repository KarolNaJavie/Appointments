package com.example.ApointmentManager.controller;

import com.example.ApointmentManager.model.CreateAppointmentCommand;
import com.example.ApointmentManager.model.FilterAppointmentCommand;
import com.example.ApointmentManager.service.AppointmentService;
import com.example.ApointmentManager.service.DoctorService;
import com.example.ApointmentManager.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {

    private final PatientService patientService;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("appointment", new CreateAppointmentCommand());
        model.addAttribute("patients", patientService.findAll());
        model.addAttribute("doctors", doctorService.findAll());
        return "create";
    }

    @PostMapping
    public String create(@ModelAttribute CreateAppointmentCommand cmd) {
        appointmentService.create(cmd);
        return "redirect:/appointments";
    }

    @GetMapping
    public String list(@ModelAttribute FilterAppointmentCommand filter, Model model) {

        model.addAttribute("appointments",
                appointmentService.findFiltered(filter));

        model.addAttribute("doctors", doctorService.findAll());
        model.addAttribute("patients", patientService.findAll());
        model.addAttribute("filter", filter);

        return "list";
    }
}
