package suntravelsl.callcenter.callcentermanagement.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import suntravelsl.callcenter.callcentermanagement.dto.ContractDTO;
import suntravelsl.callcenter.callcentermanagement.service.ContractService;

import java.util.List;

@RestController
@RequestMapping(value = "suntravels/contract")
@CrossOrigin(origins = "http://localhost:4200")

public class ContractController {
@Autowired
private ContractService contractService;

@Autowired
private ModelMapper modelMapper;

    @GetMapping("/getContracts")
    public List<ContractDTO> getContracts() {
        return contractService.getAllContracts();
    }

    @GetMapping("/getContractWithDetailsById/{id}")
    public ContractDTO getContractWithDetailsById(@PathVariable int id) {
        return contractService.getContractWithDetailsById(id);
    }

    @GetMapping("/getContractById/{id}")
    public ContractDTO getContractById(@PathVariable int id){
        return modelMapper.map(contractService.getContractById(id), ContractDTO.class);
    }

    @PostMapping("/saveContract")
    public ResponseEntity<?> saveContract(@RequestBody ContractDTO contractDTO) {
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

    @PutMapping("/updateContractById/{id}")
    public ContractDTO updateContractById(@PathVariable int id, @RequestBody ContractDTO contractDTO) {
        return contractService.updateContractById(id, contractDTO);
    }

    @DeleteMapping("/deleteContractById/{id}")
    public boolean deleteContractById(@PathVariable int id) {
        return contractService.deleteContractById(id);
    }

}
