package suntravelsl.callcenter.callcentermanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import suntravelsl.callcenter.callcentermanagement.dto.*;
import suntravelsl.callcenter.callcentermanagement.enums.Availability;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AvailabilityService {

    @Autowired
    private ContractService contractService;

    @Autowired
    private RoomTypeService roomTypeService;

        public List<AvailabilityResponseDTO> checkAvailability(AvailabilityRequestDTO request) {
            Date checkInDate = request.getCheckInDate();
            int numberOfNights = request.getNumberOfNights();

            List<ContractDTO> matchingContracts = contractService.getContractsAvailableForCheckInDate(checkInDate);
            List<AvailabilityResponseDTO> searchResults = new ArrayList<>();

            List<Integer> requestedRoomsList = request.getNumberOfRooms();
            List<Integer> requestedAdultsList = request.getNumberOfAdults();

            for (ContractDTO matchingContract : matchingContracts) {
                Date checkOutDate = addNightsToCheckInDate(checkInDate, numberOfNights);

                if (checkOutDate.before(matchingContract.getEndDate())) {
                    List<RoomTypeDTO> roomTypes = matchingContract.getRoomTypes();

                    for (RoomTypeDTO roomType : roomTypes) {
                        int numberOfRooms = roomType.getNumberOfRooms();
                        int maxAdults = roomType.getMaxAdults();

                        boolean isRoomTypeAvailable = true;

                        for (int i = 0; i < requestedRoomsList.size(); i++) {
                            int requestedRooms = requestedRoomsList.get(i);
                            int requestedAdults = requestedAdultsList.get(i);

                            if (numberOfRooms < requestedRooms || maxAdults < requestedAdults) {
                                isRoomTypeAvailable = false;
                                break;
                            }
                        }
                        Double roomTypePrice = (roomType.getPrice() * (100 + matchingContract.getMarkupPercentage())) / 100.0;

                        AvailabilityResponseDTO availabilityResponseDTO = AvailabilityResponseDTO.builder()
                                .hotelName(matchingContract.getHotel().getHotelName())
                                .roomType(roomType.getRoomTypeName())
                                .markedUpPrice(roomTypePrice)
                                .availability(isRoomTypeAvailable ? Availability.AVAILABLE : Availability.UNAVAILABLE)
                                .build();

                        searchResults.add(availabilityResponseDTO);
                    }
                }
            }

            return searchResults;
        }

        private Date addNightsToCheckInDate(Date checkInDate, int numberOfNights) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(checkInDate);
            calendar.add(Calendar.DAY_OF_MONTH, numberOfNights);
            return calendar.getTime();
        }


}


