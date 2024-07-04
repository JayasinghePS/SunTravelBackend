package suntravelsl.callcenter.callcentermanagement.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AvailabilityRequestDTO {
    private Date checkInDate;
    private int numberOfNights;

    private List<Integer> numberOfRooms;
    private List<Integer> numberOfAdults;
}
