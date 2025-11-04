package suntravelsl.callcenter.callcentermanagement.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContractDTO {
    private int contractId;
    private double markupPercentage;
    private int  hotelId;
    private Date startDate;
    private Date endDate;

    // Nested DTO – represents which hotel this contract belongs to
    private HotelDTO hotel;

    // List of room types that fall under this contract
    private List<RoomTypeDTO> roomTypes;
}
