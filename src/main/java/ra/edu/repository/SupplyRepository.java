package ra.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.edu.entity.Supply;

import java.util.List;

public interface SupplyRepository extends JpaRepository<Supply, Long> {
    List<Supply> findByDeletedFalse();

    List<Supply> findByNameContainingIgnoreCaseAndDeletedFalse(String name);

}
