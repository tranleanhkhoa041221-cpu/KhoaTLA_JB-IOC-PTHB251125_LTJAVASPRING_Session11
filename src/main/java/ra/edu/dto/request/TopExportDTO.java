package ra.edu.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TopExportDTO {
    private Long supplyId;
    private String topSupplyName;
    private Long totalExportQuantity;
}
