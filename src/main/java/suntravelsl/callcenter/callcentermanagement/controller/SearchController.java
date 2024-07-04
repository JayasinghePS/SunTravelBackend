package suntravelsl.callcenter.callcentermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import suntravelsl.callcenter.callcentermanagement.dto.AvailabilityRequestDTO;
import suntravelsl.callcenter.callcentermanagement.dto.AvailabilityResponseDTO;
import suntravelsl.callcenter.callcentermanagement.service.AvailabilityService;

import java.util.List;

@RestController
@RequestMapping(value = "suntravels")
@CrossOrigin(origins = "http://localhost:4200")

public class SearchController {
    @Autowired
    private AvailabilityService availabilityService;

    @PostMapping("/search")
    public ResponseEntity<List<AvailabilityResponseDTO>> checkAvailability(@RequestBody AvailabilityRequestDTO request) {
        List<AvailabilityResponseDTO> searchResults = availabilityService.checkAvailability(request);
        return ResponseEntity.ok(searchResults);
    }

}
