# Patient Screening System
Patient screening system with Spring Boot backend, Android, web dashboard

## How to Run

### Backend
```bash
./mvnw spring-boot:run

Backend runs on: http://localhost:8082

Web Dashboard
Open browser: http://localhost:8082/dashboard.html


Android App
1. Start backend first
2. Open project in Android Studio
3. Run on emulator (API 24+)

App connects to http://10.0.2.2:8082/api/patients


*** Features
REST API with patient data
Web dashboard table view
Android app with scrollable list


*** Tech Stack
Backend: Spring Boot, JPA, H2
Web: HTML/CSS/JavaScript
Android: Kotlin, Jetpack Compose, Retrofit
