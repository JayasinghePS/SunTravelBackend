package suntravelsl.callcenter.callcentermanagement.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import suntravelsl.callcenter.callcentermanagement.dto.HotelDTO;
import suntravelsl.callcenter.callcentermanagement.dto.RoomTypeDTO;
// import suntravelsl.callcenter.callcentermanagement.entity.Contract;
import suntravelsl.callcenter.callcentermanagement.entity.Hotel;
import suntravelsl.callcenter.callcentermanagement.exception.NotFoundException;
import suntravelsl.callcenter.callcentermanagement.exception.ResourceNotFoundException;
import suntravelsl.callcenter.callcentermanagement.repo.HotelRepo;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class HotelService {
        @Autowired
        private HotelRepo hotelRepo;    // For direct database operations on Hotel table

        @Autowired
        private RoomTypeService roomTypeService;

        @Autowired
        private ModelMapper modelMapper;


    /**
     * Save a new hotel (basic details only, no room types)
     */
    public HotelDTO saveHotel(HotelDTO hotelDTO){
        hotelRepo.save(modelMapper.map(hotelDTO, Hotel.class));
        return hotelDTO;
    }

    /**
     * Save a hotel along with its room types in one go.
     */
    public HotelDTO saveHotelWithRoomTypes(HotelDTO hotelDTO) {
        // Map HotelDTO to Hotel entity
        Hotel hotel = modelMapper.map(hotelDTO, Hotel.class);

        // Save Hotel entity
        hotel = hotelRepo.save(hotel);

        // Save associated RoomTypes
        if (hotelDTO.getRoomTypes() != null && !hotelDTO.getRoomTypes().isEmpty()) {
            for (RoomTypeDTO roomTypeDTO : hotelDTO.getRoomTypes()) {
                roomTypeDTO.setHotelId(hotel.getHotelId());
                roomTypeService.saveRoomType(roomTypeDTO);
            }
        }

        // Map and return the saved Hotel entity
        return modelMapper.map(hotel, HotelDTO.class);
    }

    /**
     * Retrieve all hotels from the database
     */
     public List<HotelDTO> getAllHotels(){
            List<Hotel> hotelList = hotelRepo.findAll();
            return modelMapper.map(hotelList, new TypeToken<List<HotelDTO>>(){}.getType());
        }


    /**
     * Fetch a single hotel by ID, including its room types.
     */
    public HotelDTO getHotelWithRoomTypesById(int id) {
        Hotel hotel = hotelRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));

        HotelDTO hotelDTO = modelMapper.map(hotel, HotelDTO.class);

        // Fetch related RoomType data
        List<RoomTypeDTO> roomTypes = hotel.getRoomTypes().stream()
                .map(roomType -> modelMapper.map(roomType, RoomTypeDTO.class))
                .collect(Collectors.toList());
        hotelDTO.setRoomTypes(roomTypes);

        return hotelDTO;
    }


    /**
     * Fetch only the hotel entity (no mapping)
     */
    public Hotel getHotelById(int id) {
        Hotel hotel = hotelRepo.findById(id).orElse(null);
        if (hotel == null) {
            throw new NotFoundException("Hotel not found with id " + id);
        }
        return hotel;
    }

     /**
     * Update hotel details (name or location)
     */
    public HotelDTO updateHotelById(int id, HotelDTO hotelDTO) {
        // Check if the hotel with the given ID exists
        Hotel existingHotel = hotelRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found with id: " + id));

        // Update specific fields based on the input provided in the HotelDTO
        if (hotelDTO.getHotelName() != null && !hotelDTO.getHotelName().isEmpty()) {
            existingHotel.setHotelName(hotelDTO.getHotelName());
        }
        if (hotelDTO.getLocation() != null && !hotelDTO.getLocation().isEmpty()) {
            existingHotel.setLocation(hotelDTO.getLocation());
        }

        // Save and return the updated hotel
        return convertToDTO(hotelRepo.save(existingHotel));
    }



    // Helper to convert entity → DTO
    private HotelDTO convertToDTO(Hotel hotel) {
        return modelMapper.map(hotel, HotelDTO.class);
    }


    /**
     * Delete a hotel by its ID.
     */
    public boolean deleteHotelById(int id) {
        // Check if the contract with the given ID exists
        if (hotelRepo.existsById(id)) {
            // Delete the contract
            hotelRepo.deleteById(id);
            return true;
        } else {
            throw new EntityNotFoundException("Hotel not found with id: " + id);
        }
    }

    }
