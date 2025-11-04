package suntravelsl.callcenter.callcentermanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import suntravelsl.callcenter.callcentermanagement.dto.*;
import suntravelsl.callcenter.callcentermanagement.enums.Availability;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service  // Marks this class as a Spring-managed Service component
public class AvailabilityService {

    @Autowired   //Inject dependencies automatically
    private ContractService contractService;  // Used to fetch valid hotel contracts for given dates

    // @Autowired
    // private RoomTypeService roomTypeService;

        /**
         * Main function to check room availability for a user request
         * @param request - includes check-in date, nights, rooms, adults, etc.
         * @return list of AvailabilityResponseDTO with availability & price
         */
        public List<AvailabilityResponseDTO> checkAvailability(AvailabilityRequestDTO request) {
            Date checkInDate = request.getCheckInDate();
            int numberOfNights = request.getNumberOfNights();

            // Get all contracts that are valid during the given check-in date
            List<ContractDTO> matchingContracts = contractService.getContractsAvailableForCheckInDate(checkInDate);
            List<AvailabilityResponseDTO> searchResults = new ArrayList<>();
            
            // Lists for rooms and adults requested per booking
            List<Integer> requestedRoomsList = request.getNumberOfRooms();
            List<Integer> requestedAdultsList = request.getNumberOfAdults();

            // Loop through each matching contract (hotel)
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

                         // Build the response DTO for this room type
                        AvailabilityResponseDTO availabilityResponseDTO = AvailabilityResponseDTO.builder()
                                .hotelName(matchingContract.getHotel().getHotelName())
                                .roomType(roomType.getRoomTypeName())
                                .markedUpPrice(roomTypePrice)
                                .availability(isRoomTypeAvailable ? Availability.AVAILABLE : Availability.UNAVAILABLE)
                                .build();

                        // Add to final results list
                        searchResults.add(availabilityResponseDTO);
                    }
                }
            }

            return searchResults;   // Return all matched room types with availability
        }

        /**
         * Helper method: adds number of nights to check-in date
         * @param checkInDate base date
         * @param numberOfNights how many nights to add
         * @return new Date object for check-out
         */
        private Date addNightsToCheckInDate(Date checkInDate, int numberOfNights) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(checkInDate);
            calendar.add(Calendar.DAY_OF_MONTH, numberOfNights);
            return calendar.getTime();
        }


}


