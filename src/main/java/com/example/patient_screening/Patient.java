package com.example.patient_screening;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Patient {
    @Id
    private Integer patientId;
    private String studyDate;
    private String sex;
    private Integer age;
    private String country;
    private String symptoms;
    private Integer geneXpert;
    private Double cadScore;
    
    public Integer getPatientId() { return patientId; }
    public void setPatientId(Integer patientId) { this.patientId = patientId; }
    public String getStudyDate() { return studyDate; }
    public void setStudyDate(String studyDate) { this.studyDate = studyDate; }
    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getSymptoms() { return symptoms; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }
    public Integer getGeneXpert() { return geneXpert; }
    public void setGeneXpert(Integer geneXpert) { this.geneXpert = geneXpert; }
    public Double getCadScore() { return cadScore; }
    public void setCadScore(Double cadScore) { this.cadScore = cadScore; }
}