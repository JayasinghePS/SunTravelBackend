package suntravelsl.callcenter.callcentermanagement.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class RoomTypeDTO {

    private int roomTypeId;
    private int  hotelId;
    private String roomTypeName;
    private double price;
    private int numberOfRooms;
    private int maxAdults;
}
