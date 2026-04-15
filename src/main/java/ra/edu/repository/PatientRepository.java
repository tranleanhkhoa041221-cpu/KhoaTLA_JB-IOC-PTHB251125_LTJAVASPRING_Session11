package ra.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.edu.entity.Patient;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByName(String name);
    List<Patient> findByAge(int age);
}
