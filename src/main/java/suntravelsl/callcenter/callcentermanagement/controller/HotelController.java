package suntravelsl.callcenter.callcentermanagement.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import suntravelsl.callcenter.callcentermanagement.dto.HotelDTO;
import suntravelsl.callcenter.callcentermanagement.service.HotelService;

import java.util.List;

@RestController
@RequestMapping(value = "suntravels/hotel")
@CrossOrigin(origins = "http://localhost:4200")

public class HotelController {
    @Autowired
    private HotelService hotelService;

    @Autowired
    private ModelMapper modelMapper;

     // GET all hotels
    @GetMapping("/getHotels")
    public List<HotelDTO> getHotels() {
        return hotelService.getAllHotels();
    }

    // GET single hotel by id
    @GetMapping("/getHotelById/{id}")
    public HotelDTO getHotelById(@PathVariable int id){
        return modelMapper.map(hotelService.getHotelById(id), HotelDTO.class);
    }

    // GET hotel along with room types
    @GetMapping("/getHotelWithRoomTypesById/{id}")
    public HotelDTO getHotelWithRoomTypesById(@PathVariable int id) {
        return hotelService.getHotelWithRoomTypesById(id);
    }

    // POST new hotel with room types
    @PostMapping("/saveHotelWithRoomTypes")
    public HotelDTO saveHotelWithRoomTypes(@RequestBody HotelDTO hotelDTO) {
        return hotelService.saveHotelWithRoomTypes(hotelDTO);
    }

    // PUT update hotel by id
    @PutMapping("/updateHotelById/{id}")
    public HotelDTO updateHotelById(@PathVariable int id, @RequestBody HotelDTO hotelDTO) {
        return hotelService.updateHotelById(id, hotelDTO);
    }

     // DELETE hotel by id
    @DeleteMapping("/deleteHotelById/{id}")
    public boolean deleteHotelById(@PathVariable int id) {
        return hotelService.deleteHotelById(id);
    }


}
