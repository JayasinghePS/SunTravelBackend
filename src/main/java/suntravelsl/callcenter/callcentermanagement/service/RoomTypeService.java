package suntravelsl.callcenter.callcentermanagement.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import suntravelsl.callcenter.callcentermanagement.dto.RoomTypeDTO;
import suntravelsl.callcenter.callcentermanagement.entity.RoomType;
import suntravelsl.callcenter.callcentermanagement.exception.NotFoundException;
import suntravelsl.callcenter.callcentermanagement.repo.RoomTypeRepo;

import java.util.List;

@Service
@Transactional
public class RoomTypeService {
    @Autowired
    private RoomTypeRepo roomTypeRepo;

    @Autowired
    private ModelMapper modelMapper;


    public RoomTypeDTO saveRoomType(RoomTypeDTO roomTypeDTO){

        roomTypeDTO.setHotelId(roomTypeDTO.getHotelId());

        roomTypeRepo.save(modelMapper.map(roomTypeDTO, RoomType.class));
        return roomTypeDTO;
    }

    public List<RoomTypeDTO> getAllRoomTypes(){
        List<RoomType> roomTypeList = roomTypeRepo.findAll();
        return modelMapper.map(roomTypeList, new TypeToken<List<RoomTypeDTO>>(){}.getType());
    }

    public RoomType getRoomTypeById(int id) {
        RoomType roomType = roomTypeRepo.findById(id).orElse(null);
        if (roomType == null) {
            throw new NotFoundException("Room Type not found with id " + id);
        }
        return roomType;
    }

    public List<RoomTypeDTO> getRoomTypesByHotelId(int hotelId) {
        List<RoomType> roomTypes = roomTypeRepo.findByHotel_HotelId(hotelId);
        return modelMapper.map(roomTypes, new TypeToken<List<RoomTypeDTO>>(){}.getType());
    }


    public RoomTypeDTO updateRoomTypeById(int id, RoomTypeDTO roomTypeDTO) {
        // Check if the hotel with the given ID exists
        RoomType existingRoomType = roomTypeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room Type not found with id: " + id));

        // Update specific fields based on the input provided in the RoomTypeDTO
        if (roomTypeDTO.getRoomTypeName() != null && !roomTypeDTO.getRoomTypeName().isEmpty()) {
            existingRoomType.setRoomTypeName(roomTypeDTO.getRoomTypeName());
        }

        if (roomTypeDTO.getPrice() > 0) {
            existingRoomType.setPrice(roomTypeDTO.getPrice());
        }

        if (roomTypeDTO.getNumberOfRooms() > 0) {
            existingRoomType.setNumberOfRooms(roomTypeDTO.getNumberOfRooms());
        }

        if (roomTypeDTO.getMaxAdults() > 0) {
            existingRoomType.setMaxAdults(roomTypeDTO.getMaxAdults());
        }

        // Save and return the updated hotel
        return convertToDTO(roomTypeRepo.save(existingRoomType));
    }


    private RoomTypeDTO convertToDTO(RoomType roomType) {
        return modelMapper.map(roomType, RoomTypeDTO.class);
    }


    public boolean deleteRoomTypeById(int id) {
        // Check if the contract with the given ID exists
        if (roomTypeRepo.existsById(id)) {
            // Delete the contract
            roomTypeRepo.deleteById(id);
            return true;
        } else {
            throw new EntityNotFoundException("Room Type not found with id: " + id);
        }
    }

}
