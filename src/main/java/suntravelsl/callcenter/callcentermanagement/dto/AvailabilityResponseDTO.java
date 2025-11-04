package suntravelsl.callcenter.callcentermanagement.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import suntravelsl.callcenter.callcentermanagement.enums.Availability;

@Data
@Builder // Enables builder pattern for easy object creation
@AllArgsConstructor // Creates constructor with all fields
@NoArgsConstructor // Creates default no-arg constructor
public class AvailabilityResponseDTO {

    private String hotelName;
    private String roomType;
    private Double markedUpPrice;

    // Enum to represent "AVAILABLE" or "UNAVAILABLE"
    @Enumerated(EnumType.STRING) // Store enum as string (not number) if mapped to DB
    private Availability availability;
}