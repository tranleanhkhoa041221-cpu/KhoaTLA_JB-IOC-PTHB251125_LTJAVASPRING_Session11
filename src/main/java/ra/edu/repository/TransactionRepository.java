package ra.edu.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ra.edu.dto.request.DailyExportDTO;
import ra.edu.dto.request.TopExportDTO;
import ra.edu.entity.Transaction;
import ra.edu.entity.TransactionType;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("""
        SELECT new ra.edu.dto.request.DailyExportDTO(
            t.supply.id,
            t.supply.name,
            SUM(t.amount)
        )
        FROM Transaction t
        WHERE t.type = :type
          AND FUNCTION('DATE', t.createdAt) = CURRENT_DATE
        GROUP BY t.supply.id, t.supply.name
    """)
    List<DailyExportDTO> getDailyExport(TransactionType type);

    @Query("""
        SELECT new ra.edu.dto.request.TopExportDTO(
            t.supply.id,
            t.supply.name,
            SUM(t.amount)
        )
        FROM Transaction t
        WHERE t.type = :type
        GROUP BY t.supply.id, t.supply.name
        ORDER BY SUM(t.amount) DESC
    """)
    List<TopExportDTO> getTopExportQuery(TransactionType type);

}
