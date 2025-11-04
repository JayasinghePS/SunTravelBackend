package suntravelsl.callcenter.callcentermanagement.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import suntravelsl.callcenter.callcentermanagement.dto.ContractDTO;
import suntravelsl.callcenter.callcentermanagement.service.ContractService;

import java.util.List;

@RestController // marks this class as a REST controller (returns JSON data by default)
@RequestMapping(value = "suntravels/contract") // base URL path for all endpoints in this controller
@CrossOrigin(origins = "http://localhost:4200") // allows frontend (Angular) to access this API

public class ContractController {
@Autowired
private ContractService contractService;

@Autowired
private ModelMapper modelMapper;

    // GET all contracts
    @GetMapping("/getContracts")
    public List<ContractDTO> getContracts() {
        return contractService.getAllContracts();
    }

    // GET contract with full details (hotel + rooms)
    @GetMapping("/getContractWithDetailsById/{id}")
    public ContractDTO getContractWithDetailsById(@PathVariable int id) { // @PathVariable binds {id} in URL to parameter
        return contractService.getContractWithDetailsById(id);
    }

    // GET contract by id (simple details only)
    @GetMapping("/getContractById/{id}")
    public ContractDTO getContractById(@PathVariable int id){
        return modelMapper.map(contractService.getContractById(id), ContractDTO.class);
    }

     // POST new contract
    @PostMapping("/saveContract")
    public ResponseEntity<?> saveContract(@RequestBody ContractDTO contractDTO) { // @RequestBody converts incoming JSON into ContractDTO object
        try {
            ContractDTO savedContract = contractService.saveContract(contractDTO);
            return ResponseEntity.ok(savedContract);
        } catch (IllegalArgumentException e) {
            // Handle validation errors
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    // PUT update contract by id
    @PutMapping("/updateContractById/{id}")
    public ContractDTO updateContractById(@PathVariable int id, @RequestBody ContractDTO contractDTO) {
        return contractService.updateContractById(id, contractDTO);
    }

    // DELETE contract by id
    @DeleteMapping("/deleteContractById/{id}")
    public boolean deleteContractById(@PathVariable int id) {
        return contractService.deleteContractById(id);
    }

}
