package ra.edu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ra.edu.dto.request.PatientDTO;
import ra.edu.entity.Patient;
import ra.edu.repository.PatientRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {
    private final PatientRepository patientRepository;

    public Patient create(PatientDTO request) {
        log.info("Creating a new patient with name {} and age {}", request.getName(), request.getAge());
        if (request.getAge() > 120) {
            log.warn("Age is greater than 120 with full information : name {} and age {}", request.getName(), request.getAge());
        }
        Patient patient = new Patient();
        patient.setName(request.getName());
        patient.setAge(request.getAge());
        return patientRepository.save(patient);


    }

    public List<Patient> searchByName(String name) {
        log.info("Searching for patient with name = {}", name);
        return patientRepository.findByName(name);

    }

    public List<Patient> searchByAge(int age) {
        log.info("Searching for patient with age = {}", age);
        return patientRepository.findByAge(age);
    }

    public int divide(int a, int b) {

        log.info("CALL DIVIDE a = {} b = {}", a, b);

        return a / b;
    }

}
