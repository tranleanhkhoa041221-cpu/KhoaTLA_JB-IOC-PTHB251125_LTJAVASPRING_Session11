package ra.edu.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DailyExportDTO {
    private Long supplyId;
    private String supplyName;
    private Long totalExportQuantity;
}
