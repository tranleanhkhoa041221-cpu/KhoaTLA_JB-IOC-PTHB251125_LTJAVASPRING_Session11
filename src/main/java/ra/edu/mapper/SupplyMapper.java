package ra.edu.mapper;


import ra.edu.dto.response.SupplyResponse;
import ra.edu.entity.Supply;

public class SupplyMapper {
    public static SupplyResponse toResponse(Supply s) {
        SupplyResponse dto = new SupplyResponse();
        dto.setId(s.getId());
        dto.setName(s.getName());
        dto.setSpecification(s.getSpecification());
        dto.setProvider(s.getProvider());
        dto.setQuantity(s.getQuantity());
        dto.setCreatedAt(s.getCreatedAt());
        dto.setUpdatedAt(s.getUpdatedAt());

        return dto;
    }
}
