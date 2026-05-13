package com.example.patient_screening;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class CSVDataLoader implements CommandLineRunner {
    private final PatientRepository repository;
    
    public CSVDataLoader(PatientRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public void run(String... args) throws Exception {
        if (repository.count() > 0) return;
        
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("/data.csv")))) {
            String line = reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length < 8) continue;
                
                Patient patient = new Patient();
                patient.setPatientId(parseInt(fields[0]));
                patient.setStudyDate(fields[1]);
                patient.setSex(fields[2]);
                patient.setAge(parseInt(fields[3]));
                patient.setCountry(fields[4].equals("NaN") ? null : fields[4]);
                patient.setSymptoms(fields[5]);
                patient.setGeneXpert(parseInt(fields[6]));
                patient.setCadScore(parseDouble(fields[7]));
                repository.save(patient);
            }
            System.out.println("Loaded " + repository.count() + " patients");
        }
    }
    
    private Integer parseInt(String val) {
        try { return val == null || val.isEmpty() ? null : Integer.parseInt(val); }
        catch (NumberFormatException e) { return null; }
    }
    
    private Double parseDouble(String val) {
        try { return val == null || val.isEmpty() || val.equals("NaN") ? null : Double.parseDouble(val); }
        catch (NumberFormatException e) { return null; }
    }
}