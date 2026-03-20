package com.med.doctorss.controller;

import com.med.doctorss.doctor.DataRegisterDoc;
import com.med.doctorss.doctor.Doctor;
import com.med.doctorss.doctor.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository repository;

    @PostMapping
    public void register(@RequestBody DataRegisterDoc data) {
        repository.save(new Doctor(data));
    }
}