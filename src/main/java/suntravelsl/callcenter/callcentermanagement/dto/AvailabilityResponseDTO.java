package suntravelsl.callcenter.callcentermanagement.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import suntravelsl.callcenter.callcentermanagement.enums.Availability;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityResponseDTO {

    private String hotelName;
    private String roomType;
    private Double markedUpPrice;

    @Enumerated(EnumType.STRING)
    private Availability availability;
}