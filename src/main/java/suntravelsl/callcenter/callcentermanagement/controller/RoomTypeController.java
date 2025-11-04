package suntravelsl.callcenter.callcentermanagement.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import suntravelsl.callcenter.callcentermanagement.dto.RoomTypeDTO;
import suntravelsl.callcenter.callcentermanagement.service.RoomTypeService;

import java.util.List;

@RestController
@RequestMapping(value = "suntravels/roomtype")
@CrossOrigin
public class RoomTypeController {
    @Autowired
    private RoomTypeService roomTypeService;

    @Autowired
    private ModelMapper modelMapper;

    // GET all room types
    @GetMapping("/getRoomTypes")
    public List<RoomTypeDTO> getRoomTypes() {
        return roomTypeService.getAllRoomTypes();
    }

    // GET room type by id
    @GetMapping("/getRoomTypeById/{id}")
    public RoomTypeDTO getRoomTypeById(@PathVariable int id){
        return modelMapper.map(roomTypeService.getRoomTypeById(id), RoomTypeDTO.class);
    }

    // GET all room types for a specific hotel
    @GetMapping("/getRoomTypesByHotelId/{hotelId}")
    public List<RoomTypeDTO> getRoomTypesByHotelId(@PathVariable int hotelId) {
        return roomTypeService.getRoomTypesByHotelId(hotelId);
    }

    // POST new room type
    @PostMapping("/saveRoomType")
    public RoomTypeDTO saveRoomType(@RequestBody RoomTypeDTO roomTypeDTO) {
        return roomTypeService.saveRoomType(roomTypeDTO);
    }

    // PUT update room type by id
    @PutMapping("/updateRoomTypeById/{id}")
    public RoomTypeDTO updateRoomTypeById(@PathVariable int id, @RequestBody RoomTypeDTO roomTypeDTO) {
        return roomTypeService.updateRoomTypeById(id, roomTypeDTO);
    }

    // DELETE room type by id
    @DeleteMapping("/deleteRoomTypeById/{id}")
    public boolean deleteRoomTypeById(@PathVariable int id) {
        return roomTypeService.deleteRoomTypeById(id);
    }


}
