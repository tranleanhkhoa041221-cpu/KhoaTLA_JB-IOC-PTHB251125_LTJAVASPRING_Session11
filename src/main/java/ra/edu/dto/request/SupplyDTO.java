package ra.edu.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SupplyDTO {

    @NotBlank(message = "Tên vật tư không được để trống")
    private String name;

    private String specification;

    private String provider;
}
