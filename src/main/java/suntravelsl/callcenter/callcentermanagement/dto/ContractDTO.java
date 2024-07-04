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

    private HotelDTO hotel;

    private List<RoomTypeDTO> roomTypes;
}
