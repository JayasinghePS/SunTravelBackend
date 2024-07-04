package suntravelsl.callcenter.callcentermanagement.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import suntravelsl.callcenter.callcentermanagement.CallcentermanagementApplication;
import suntravelsl.callcenter.callcentermanagement.dto.HotelDTO;
import suntravelsl.callcenter.callcentermanagement.dto.RoomTypeDTO;
import suntravelsl.callcenter.callcentermanagement.entity.Hotel;
import suntravelsl.callcenter.callcentermanagement.repo.HotelRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {CallcentermanagementApplication.class})
class HotelServiceTest {
    @InjectMocks
    private HotelService hotelService;

    @InjectMocks
    private RoomTypeService roomTypeService;

    @Mock
    private HotelRepo hotelRepo;

    @Mock
    private ModelMapper modelMapper;


//    @Test
//    public void testSaveHotelWithRoomTypes() {
//        // Create a sample HotelDTO with RoomTypes
//        HotelDTO inputHotelDTO = new HotelDTO();
//        inputHotelDTO.setHotelName("Test Hotel");
//        inputHotelDTO.setLocation("Test Location");
//
//        List<RoomTypeDTO> roomTypes = new ArrayList<>();
//
//        RoomTypeDTO roomTypeDTO1 = new RoomTypeDTO();
//        roomTypeDTO1.setRoomTypeName("Single Room");
//        roomTypeDTO1.setPrice(100.0);
//        roomTypeDTO1.setNumberOfRooms(10);
//        roomTypeDTO1.setMaxAdults(2);
//        roomTypes.add(roomTypeDTO1);
//
//        RoomTypeDTO roomTypeDTO2 = new RoomTypeDTO();
//        roomTypeDTO1.setHotelId(1);
//        roomTypeDTO2.setRoomTypeName("Double Room");
//        roomTypeDTO2.setPrice(150.0);
//        roomTypeDTO2.setNumberOfRooms(5);
//        roomTypeDTO2.setMaxAdults(4);
//        roomTypes.add(roomTypeDTO2);
//
//        inputHotelDTO.setRoomTypes(roomTypes);
//
//        // Mock the behavior of hotelRepo.save and roomTypeService.saveRoomType
//        when(hotelRepo.save(any())).thenAnswer(invocation -> {
//            HotelDTO hotelDTOArgument = invocation.getArgument(0);
//            hotelDTOArgument.setHotelId(1); // Mock a returned hotelId
//            return modelMapper.map(hotelDTOArgument, Hotel.class);
//        });
//
//        when(roomTypeService.saveRoomType(any())).thenAnswer(invocation -> {
//            RoomTypeDTO roomTypeDTOArgument = invocation.getArgument(0);
//            roomTypeDTOArgument.setRoomTypeId(1); // Mock a returned roomTypeId
//            return roomTypeDTOArgument;
//        });
//
//        // Call the method to be tested
//        HotelDTO resultHotelDTO = hotelService.saveHotelWithRoomTypes(inputHotelDTO);
//
//        // Assertions
//        assertNotNull(resultHotelDTO);
//        assertEquals("Test Hotel", resultHotelDTO.getHotelName());
//        assertEquals("Test Location", resultHotelDTO.getLocation());
//        assertNotNull(resultHotelDTO.getRoomTypes());
//        assertEquals(2, resultHotelDTO.getRoomTypes().size());
//
//        // Verify that hotelRepo.save and roomTypeService.saveRoomType were called with the expected arguments
//        verify(hotelRepo, times(1)).save(any());
//        for (RoomTypeDTO roomTypeDTO : roomTypes) {
//            roomTypeDTO.setHotelId(resultHotelDTO.getHotelId());
//            verify(roomTypeService, times(1)).saveRoomType(eq(roomTypeDTO));
//        }
//    }

//    @Test
//    void testGetAllHotels() {
//        // Arrange
//        MockitoAnnotations.openMocks(this); // Initializes the mocks
//
//        // Mocking behavior of hotelRepo.findAll()
//        Hotel hotel1 = new Hotel();
//        hotel1.setHotelId(1);
//        hotel1.setHotelName("Hotel 1");
//        hotel1.setLocation("Location 1");
//
//        Hotel hotel2 = new Hotel();
//        hotel2.setHotelId(2);
//        hotel2.setHotelName("Hotel 2");
//        hotel2.setLocation("Location 2");
//
//        List<Hotel> mockHotelList = Arrays.asList(hotel1, hotel2);
//
//        when(hotelRepo.findAll()).thenReturn(mockHotelList);
//
//        // Mocking behavior of modelMapper.map()
//        when(modelMapper.map(hotel1, HotelDTO.class)).thenReturn(new HotelDTO(1, "Hotel 1", "Location 1", null));
//        when(modelMapper.map(hotel2, HotelDTO.class)).thenReturn(new HotelDTO(2, "Hotel 2", "Location 2", null));
//
//        // Act
//        List<HotelDTO> result = hotelService.getAllHotels();
//
//        // Assert
//        assertEquals(2, result.size());
//        assertEquals("Hotel 1", result.get(0).getHotelName());
//        assertEquals("Location 1", result.get(0).getLocation());
//        assertEquals("Hotel 2", result.get(1).getHotelName());
//        assertEquals("Location 2", result.get(1).getLocation());
//    }


    @Test
    void saveHotel() {
        // Mock data
        HotelDTO hotelDTO = new HotelDTO(1, "Hotel One", "Location One", null);

        when(modelMapper.map(hotelDTO, Hotel.class)).thenReturn(new Hotel());
        when(hotelRepo.save(Mockito.any())).thenReturn(new Hotel());

        // Call the service method
        HotelDTO result = hotelService.saveHotel(hotelDTO);

        // Assertions
        assertEquals(hotelDTO, result);
    }

//    @Test
//    void saveHotelWithRoomTypes() {
//        // Mock data
//        HotelDTO hotelDTO = new HotelDTO(1, "Hotel One", "Location One",
//                Arrays.asList(new RoomTypeDTO(1, 1, "Single", 100.0, 1, 1)));
//
//        when(modelMapper.map(hotelDTO, Hotel.class)).thenReturn(new Hotel());
//        when(hotelRepo.save(Mockito.any())).thenReturn(new Hotel());
//        when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(new RoomTypeDTO());
//
//        // Call the service method
//        HotelDTO result = hotelService.saveHotelWithRoomTypes(hotelDTO);
//
//        // Assertions
//        assertEquals(hotelDTO, result);
//    }

//    @Test
//    void getAllHotels() {
//        // Mock data
//        List<Hotel> hotelList = Arrays.asList(new Hotel(), new Hotel());
//
//        when(hotelRepo.findAll()).thenReturn(hotelList);
//        when(modelMapper.map(hotelList, new TypeToken() {}.getType()))
//                .thenReturn(Arrays.asList(new HotelDTO(), new HotelDTO()));
//
//        // Call the service method
//        List<HotelDTO> result = hotelService.getAllHotels();
//
//        // Assertions
//        assertEquals(2, result.size());
//    }

//    @Test
//    void getHotelWithRoomTypesById() {
//        // Mock data
//        int hotelId = 1;
//        Hotel hotel = new Hotel();
//        hotel.setHotelId(hotelId);
//        HotelDTO hotelDTO = new HotelDTO();
//        hotelDTO.setHotelId(hotelId);
//
//        when(hotelRepo.findById(hotelId)).thenReturn(java.util.Optional.of(hotel));
//        when(modelMapper.map(hotel, HotelDTO.class)).thenReturn(hotelDTO);
//
//        // Call the service method
//        HotelDTO result = hotelService.getHotelWithRoomTypesById(hotelId);
//
//        // Assertions
//        assertEquals(hotelDTO, result);
//    }

//    @Test
//    void updateHotelById() {
//        // Mock data
//        int hotelId = 1;
//        HotelDTO hotelDTO = new HotelDTO(hotelId, "Updated Hotel", "Updated Location", null);
//        Hotel existingHotel = new Hotel();
//        existingHotel.setHotelId(hotelId);
//
//        when(hotelRepo.findById(hotelId)).thenReturn(java.util.Optional.of(existingHotel));
//        when(hotelRepo.save(Mockito.any())).thenReturn(existingHotel);
//        when(modelMapper.map(existingHotel, HotelDTO.class)).thenReturn(new HotelDTO());
//
//        // Call the service method
//        HotelDTO result = hotelService.updateHotelById(hotelId, hotelDTO);
//
//        // Assertions
//        assertEquals(existingHotel.getHotelId(), result.getHotelId());
//        assertEquals(existingHotel.getHotelName(), result.getHotelName());
//        assertEquals(existingHotel.getLocation(), result.getLocation());
//    }


    //delete
    @Test
    void testDeleteHotelById_Exists() {
        int hotelId = 1;

        when(hotelRepo.existsById(hotelId)).thenReturn(true);

        boolean result = hotelService.deleteHotelById(hotelId);

        assertTrue(result);
        verify(hotelRepo, times(1)).deleteById(hotelId);
    }

    @Test
    void testDeleteHotelById_NotExists() {
        int hotelId = 1;

        when(hotelRepo.existsById(hotelId)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                hotelService.deleteHotelById(hotelId));

        assertEquals("Hotel not found with id: " + hotelId, exception.getMessage());
        verify(hotelRepo, never()).deleteById(hotelId);
    }
}