package com.example.patient_screening;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientRepository repository;
    
    public PatientController(PatientRepository repository) {
        this.repository = repository;
    }
    
    @GetMapping
    public List<Patient> getAllPatients() {
        return repository.findAll();
    }
}