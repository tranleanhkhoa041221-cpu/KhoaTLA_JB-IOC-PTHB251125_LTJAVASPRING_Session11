package ra.edu.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.edu.dto.request.PatientDTO;
import ra.edu.entity.Patient;
import ra.edu.service.PatientService;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@Slf4j
public class PatientController {
    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PatientDTO request) {
        log.info("API CREATE patient");
        Patient patient = patientService.create(request);
        log.info("Patient created successfully with id: {}", patient.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(patient);

    }

    @GetMapping("/search-name")
    public ResponseEntity<?> findByName(@RequestParam String name) {
        log.info("API SEARCH patient by name");

        var result = patientService.searchByName(name);

        log.info("FOUND {} patients", result.size());

        return ResponseEntity.ok(result);
    }

    @GetMapping("/search-age")
    public ResponseEntity<?> findByAge(@RequestParam int age) {
        log.info("API SEARCH patient by age");

        var result = patientService.searchByAge(age);

        log.info("FOUND {} patients", result.size());

        return ResponseEntity.ok(result);
    }

    @GetMapping("/divide")
    public ResponseEntity<?> divide(@RequestParam int a,
                                    @RequestParam int b) {

        log.info("API DIVIDE a/b");

        int result = patientService.divide(a, b);

        log.info("DIVIDE RESULT =  {}", result);

        return ResponseEntity.ok(result);
    }

}
