package suntravelsl.callcenter.callcentermanagement.service;

import com.google.gson.reflect.TypeToken;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import suntravelsl.callcenter.callcentermanagement.CallcentermanagementApplication;
import suntravelsl.callcenter.callcentermanagement.dto.RoomTypeDTO;
import suntravelsl.callcenter.callcentermanagement.entity.Hotel;
import suntravelsl.callcenter.callcentermanagement.entity.RoomType;
import suntravelsl.callcenter.callcentermanagement.exception.NotFoundException;
import suntravelsl.callcenter.callcentermanagement.repo.RoomTypeRepo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {CallcentermanagementApplication.class})
@ExtendWith(MockitoExtension.class)
class RoomTypeServiceTest {

    @Mock
    private RoomTypeRepo roomTypeRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RoomTypeService roomTypeService;

    @Test
    void testSaveRoomType() {
        // Mocking the behavior of RoomTypeRepo.save()
        RoomTypeDTO roomTypeDTO = new RoomTypeDTO();
        roomTypeDTO.setHotelId(1);
        roomTypeDTO.setRoomTypeName("Deluxe");
        roomTypeDTO.setPrice(200.0);
        roomTypeDTO.setNumberOfRooms(10);
        roomTypeDTO.setMaxAdults(2);

        RoomType roomType = new RoomType();
        roomType.setRoomTypeId(1);
        roomType.setHotel(createMockHotel(1));
        roomType.setRoomTypeName("Deluxe");
        roomType.setPrice(200.0);
        roomType.setNumberOfRooms(10);
        roomType.setMaxAdults(2);

        when(modelMapper.map(roomTypeDTO, RoomType.class)).thenReturn(roomType);
        when(roomTypeRepo.save(any(RoomType.class))).thenReturn(roomType);

        // Call the method to be tested
        RoomTypeDTO result = roomTypeService.saveRoomType(roomTypeDTO);

        // Assertions
        assertEquals(roomTypeDTO.getHotelId(), result.getHotelId());
        assertEquals(roomTypeDTO.getRoomTypeName(), result.getRoomTypeName());
        assertEquals(roomTypeDTO.getPrice(), result.getPrice());
        assertEquals(roomTypeDTO.getNumberOfRooms(), result.getNumberOfRooms());
        assertEquals(roomTypeDTO.getMaxAdults(), result.getMaxAdults());
    }

    private Hotel createMockHotel(int hotelId) {
        Hotel hotel = new Hotel();
        hotel.setHotelId(hotelId);
        // Set other properties as needed
        return hotel;
    }

//    @Test
//    void testGetAllRoomTypes() {
//        // Mocking the behavior of RoomTypeRepo.findAll()
//        RoomType roomType1 = new RoomType();
//        roomType1.setRoomTypeId(2);
//        roomType1.setHotel(createMockHotel(2));
//        roomType1.setRoomTypeName("Deluxe");
//        roomType1.setPrice(200.0);
//        roomType1.setNumberOfRooms(15);
//        roomType1.setMaxAdults(11);
//
//        RoomType roomType2 = new RoomType();
//        roomType2.setRoomTypeId(4);
//        roomType2.setHotel(createMockHotel(4));
//        roomType2.setRoomTypeName("Suite");
//        roomType2.setPrice(180.0);
//        roomType2.setNumberOfRooms(18);
//        roomType2.setMaxAdults(3);
//
//        // Add more RoomType objects as needed
//
//        List<RoomType> roomTypeList = Arrays.asList(roomType1, roomType2);
//
//        // Mocking the behavior of ModelMapper.map()
//        RoomTypeDTO roomTypeDTO1 = new RoomTypeDTO();
//        roomTypeDTO1.setRoomTypeId(2);
//        roomTypeDTO1.setHotelId(2);
//        roomTypeDTO1.setRoomTypeName("Deluxe");
//        roomTypeDTO1.setPrice(200.0);
//        roomTypeDTO1.setNumberOfRooms(15);
//        roomTypeDTO1.setMaxAdults(11);
//
//        RoomTypeDTO roomTypeDTO2 = new RoomTypeDTO();
//        roomTypeDTO2.setRoomTypeId(4);
//        roomTypeDTO2.setHotelId(4);
//        roomTypeDTO2.setRoomTypeName("Suite");
//        roomTypeDTO2.setPrice(180.0);
//        roomTypeDTO2.setNumberOfRooms(18);
//        roomTypeDTO2.setMaxAdults(3);
//
//        // Add more RoomTypeDTO objects as needed
//
//        List<RoomTypeDTO> roomTypeDTOList = Arrays.asList(roomTypeDTO1, roomTypeDTO2);
//
//        when(roomTypeRepo.findAll()).thenReturn(roomTypeList);
//        when(modelMapper.map(roomTypeList, List.class)).thenReturn(roomTypeDTOList);
//
//        // Call the method to be tested
//        List<RoomTypeDTO> result = roomTypeService.getAllRoomTypes();
//
//        // Assertions
//        assertEquals(roomTypeList.size(), result.size());
//
//        // Verify assertions for each RoomTypeDTO
//        assertEquals(roomType1.getRoomTypeId(), result.get(0).getRoomTypeId());
//        assertEquals(roomType1.getHotel().getHotelId(), result.get(0).getHotelId());
//        assertEquals(roomType1.getRoomTypeName(), result.get(0).getRoomTypeName());
//        assertEquals(roomType1.getPrice(), result.get(0).getPrice());
//        assertEquals(roomType1.getNumberOfRooms(), result.get(0).getNumberOfRooms());
//        assertEquals(roomType1.getMaxAdults(), result.get(0).getMaxAdults());
//
//        // Repeat the assertions for other RoomTypeDTO objects in the result list
//    }


    @Test
    void testDeleteRoomTypeById_Exists() {
        int roomId = 1;

        when(roomTypeRepo.existsById(roomId)).thenReturn(true);

        boolean result = roomTypeService.deleteRoomTypeById(roomId);

        assertTrue(result);
        verify(roomTypeRepo, times(1)).deleteById(roomId);
    }

    @Test
    void testDeleteRoomTypeById_NotExists() {
        int roomId = 1;

        when(roomTypeRepo.existsById(roomId)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                roomTypeService.deleteRoomTypeById(roomId));

        assertEquals("Room Type not found with id: " + roomId, exception.getMessage());
        verify(roomTypeRepo, never()).deleteById(roomId);
    }

//    @Test
//    void getAllRoomTypes() {
//        // Mock data
//        RoomTypeDTO roomTypeDTO1 = new RoomTypeDTO(1, 1, "Single", 100.0, 1, 1);
//        RoomTypeDTO roomTypeDTO2 = new RoomTypeDTO(2, 1, "Double", 150.0, 2, 2);
//        List<RoomTypeDTO> mockRoomTypeDTOList = Arrays.asList(roomTypeDTO1, roomTypeDTO2);
//
//        when(roomTypeRepo.findAll()).thenReturn(Arrays.asList(Mockito.mock(RoomType.class), Mockito.mock(RoomType.class)));
//        when(modelMapper.map(ArgumentMatchers.anyList(), Mockito.any())).thenReturn(mockRoomTypeDTOList);
//
//        // Call the service method
//        List<RoomTypeDTO> result = roomTypeService.getAllRoomTypes();
//
//        // Assertions
//        assertEquals(2, result.size());
//
//        // Assertion for the first room type
//        assertEquals(1, result.get(0).getRoomTypeId());
//        assertEquals(1, result.get(0).getHotelId());
//        assertEquals("Single", result.get(0).getRoomTypeName());
//        assertEquals(100.0, result.get(0).getPrice());
//        assertEquals(1, result.get(0).getNumberOfRooms());
//        assertEquals(1, result.get(0).getMaxAdults());
//
//        // Assertion for the second room type
//        assertEquals(2, result.get(1).getRoomTypeId());
//        assertEquals(1, result.get(1).getHotelId());
//        assertEquals("Double", result.get(1).getRoomTypeName());
//        assertEquals(150.0, result.get(1).getPrice());
//        assertEquals(2, result.get(1).getNumberOfRooms());
//        assertEquals(2, result.get(1).getMaxAdults());
//
//        // Add more assertions based on your data
//    }

    @Test
    public void testGetRoomTypeById() {
        // Arrange
        int roomId = 1;
        RoomType roomTypeEntity = new RoomType();
        when(roomTypeRepo.findById(roomId)).thenReturn(Optional.of(roomTypeEntity));

        // Act
        RoomType result = roomTypeService.getRoomTypeById(roomId);

        // Assert
        assertNotNull(result);
        assertSame(roomTypeEntity, result);
    }

    @Test
    public void testGetRoomTypeByIdNotFound() {
        // Arrange
        int roomId = 1;
        when(roomTypeRepo.findById(roomId)).thenReturn(Optional.empty());

        // Assert
        assertThrows(NotFoundException.class, () -> roomTypeService.getRoomTypeById(roomId));
    }

    @Test
    public void testUpdateRoomTypeById() {
        // Arrange
        int roomId = 1;
        RoomTypeDTO roomTypeDTO = new RoomTypeDTO();
        roomTypeDTO.setRoomTypeName("Updated Room Type");
        roomTypeDTO.setPrice(150.0);
        roomTypeDTO.setNumberOfRooms(10);
        roomTypeDTO.setMaxAdults(2);

        RoomType existingRoomType = new RoomType();
        existingRoomType.setRoomTypeId(roomId);  // Assuming your RoomType entity has an id field
        existingRoomType.setRoomTypeName("Old Room Type");
        existingRoomType.setPrice(100.0);
        existingRoomType.setNumberOfRooms(5);
        existingRoomType.setMaxAdults(1);

        // Mocking repository behavior
        when(roomTypeRepo.findById(roomId)).thenReturn(Optional.of(existingRoomType));
        when(roomTypeRepo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));  // Mocking save method to return the input

        // Mocking modelMapper behavior
        when(modelMapper.map(any(), eq(RoomTypeDTO.class))).thenReturn(roomTypeDTO);

        // Act
        RoomTypeDTO updatedRoomTypeDTO = roomTypeService.updateRoomTypeById(roomId, roomTypeDTO);

        // Assert
        assertNotNull(updatedRoomTypeDTO);
        assertEquals("Updated Room Type", updatedRoomTypeDTO.getRoomTypeName());
        assertEquals(150.0, updatedRoomTypeDTO.getPrice());
        assertEquals(10, updatedRoomTypeDTO.getNumberOfRooms());
        assertEquals(2, updatedRoomTypeDTO.getMaxAdults());
        verify(roomTypeRepo, times(1)).save(existingRoomType);  // Verify that save method is called with the correct entity
        verify(modelMapper, times(1)).map(existingRoomType, RoomTypeDTO.class);  // Verify that map method is called with the correct parameters
    }


}