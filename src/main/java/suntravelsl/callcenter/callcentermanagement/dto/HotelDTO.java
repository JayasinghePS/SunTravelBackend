package suntravelsl.callcenter.callcentermanagement.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Component //marks this class as a Spring component
public class HotelDTO {
    private int hotelId;
    private String hotelName;
    private String location;

    private List<RoomTypeDTO> roomTypes;
}
