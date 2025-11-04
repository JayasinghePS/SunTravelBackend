package suntravelsl.callcenter.callcentermanagement.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter //normally no need this when you have @Data already where make both getters and setters
@Setter //normally no need this when you have @Data already where make both getters and setters
@Component //marks this class as a Spring component[Spring-managed bean] (optional here, usually not required for DTOs)
public class HotelDTO {
    private int hotelId;
    private String hotelName;
    private String location;

    private List<RoomTypeDTO> roomTypes;
}
