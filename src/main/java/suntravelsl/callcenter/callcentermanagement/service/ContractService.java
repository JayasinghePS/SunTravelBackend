package suntravelsl.callcenter.callcentermanagement.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import suntravelsl.callcenter.callcentermanagement.dto.ContractDTO;
import suntravelsl.callcenter.callcentermanagement.dto.HotelDTO;
import suntravelsl.callcenter.callcentermanagement.dto.RoomTypeDTO;
import suntravelsl.callcenter.callcentermanagement.entity.Contract;
import suntravelsl.callcenter.callcentermanagement.entity.Hotel;
import suntravelsl.callcenter.callcentermanagement.exception.NotFoundException;
import suntravelsl.callcenter.callcentermanagement.exception.ResourceNotFoundException;
import suntravelsl.callcenter.callcentermanagement.repo.ContractRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import suntravelsl.callcenter.callcentermanagement.repo.HotelRepo;
// import suntravelsl.callcenter.callcentermanagement.repo.RoomTypeRepo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional  // Ensures all DB operations inside methods are executed in a single transaction
public class ContractService {
    @Autowired
    private ContractRepo contractRepo;

    @Autowired
    private HotelRepo hotelRepository;

    // @Autowired
    // private RoomTypeRepo roomTypeRepository;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private ModelMapper modelMapper;    // Helps map between Entity and DTO classes


    /**
     * Saves a new contract for a hotel.
     * Maps DTO to Entity, validates date overlaps, and saves it.
     */
    public ContractDTO saveContract(ContractDTO contractDTO) {
        // Map ContractDTO to Contract entity
        Contract contract = modelMapper.map(contractDTO, Contract.class);

        // Fetch the Hotel entity by its ID and set it in the Contract entity
        Hotel hotel = hotelRepository.findById(contractDTO.getHotelId())
                .orElseThrow(() -> new NotFoundException("Hotel not found with id " + contractDTO.getHotelId()));
        contract.setHotel(hotel);

        // Validate the contract dates
        validateContractDates(contractDTO);

        // Save the Contract entity to the database
        contractRepo.save(contract);

        // Return the saved ContractDTO
        return contractDTO;
    }


    /**
     * Validates that new contract dates don’t overlap with existing contracts
     * for the same hotel.
     */
    private void validateContractDates(ContractDTO newContractDTO) {
        List<Contract> existingContracts = contractRepo.findByHotelHotelId(newContractDTO.getHotelId());


        // Check if the start date of the new contract is before the end date of an existing contract,
        // and if the end date of the new contract is after the start date of the existing contract.
        // If there is an overlap, throw an IllegalArgumentException indicating the presence of overlapping dates.

        for (Contract existingContract : existingContracts) {
            if (newContractDTO.getStartDate().before(existingContract.getEndDate()) &&
                    newContractDTO.getEndDate().after(existingContract.getStartDate())) {
                throw new IllegalArgumentException("Invalid contract dates. There is already a contract with overlapping dates for the same hotel.");
            }

            if (newContractDTO.getStartDate().compareTo(existingContract.getEndDate()) <= 0 &&
                    newContractDTO.getEndDate().compareTo(existingContract.getStartDate()) > 0) {
                throw new IllegalArgumentException("Invalid contract dates. There is already a contract with overlapping dates for the same hotel.");
            }
        }
    }



     /**
     * Fetches all contracts in the system and maps them to DTOs.
     */
    public List<ContractDTO> getAllContracts() {
        List<Contract> contractList = contractRepo.findAll();
        return modelMapper.map(contractList, new TypeToken<List<ContractDTO>>() {}.getType());
    }

    /**
     * Fetch a single contract by its ID.
     */
    public Contract getContractById(int id) {
        Contract contract = contractRepo.findById(id).orElse(null);
        if (contract == null) {
            throw new NotFoundException("Contract not found with id " + id);
        }
        return contract;
    }


    /**
     * Update certain fields in an existing contract by its ID.
     */    
    public ContractDTO updateContractById(int id, ContractDTO contractDTO) {
        // Check if the contract with the given ID exists
        Contract existingContract = contractRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contract not found with id: " + id));

        // Update specific fields based on the input provided in the ContractDTO
        if (contractDTO.getMarkupPercentage() > 0) {
            existingContract.setMarkupPercentage(contractDTO.getMarkupPercentage());
        }

        if (contractDTO.getStartDate() != null) {
            existingContract.setStartDate(contractDTO.getStartDate());
        }

        if (contractDTO.getEndDate() != null) {
            existingContract.setEndDate(contractDTO.getEndDate());
        }

        // Save and return the updated contract
        return convertToDTO(contractRepo.save(existingContract));
    }


     // Helper to convert Entity → DTO
    private ContractDTO convertToDTO(Contract contract) {
        return modelMapper.map(contract, ContractDTO.class);
    }


    /**
     * Deletes a contract by its ID.
     */
    public boolean deleteContractById(int id) {
        // Check if the contract with the given ID exists
        if (contractRepo.existsById(id)) {
            // Delete the contract
            contractRepo.deleteById(id);
            return true;
        } else {
            throw new NotFoundException("Contract not found with id: " + id);
        }
    }

    // here it take all contract details, hotel details and room type details matching to a contract id
    public ContractDTO getContractWithDetailsById(int id) {
        Contract contract = contractRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + id));

        ContractDTO contractDTO = modelMapper.map(contract, ContractDTO.class);

        // Fetch related Hotel data
        HotelDTO hotelDTO = modelMapper.map(contract.getHotel(), HotelDTO.class);
        contractDTO.setHotel(hotelDTO);

        // Fetch related RoomType data
        List<RoomTypeDTO> roomTypes = contract.getHotel().getRoomTypes().stream()
                .map(roomType -> modelMapper.map(roomType, RoomTypeDTO.class))
                .collect(Collectors.toList());
        contractDTO.setRoomTypes(roomTypes);

        return contractDTO;
    }

    /**
     * Returns all contracts that are valid for a given check-in date.
     * Used by AvailabilityService.
     */
    public List<ContractDTO> getContractsAvailableForCheckInDate(Date checkInDate) {

        List<ContractDTO> allContracts = getAllContracts();
        List<ContractDTO> matchingContracts = new ArrayList<>();

        for (ContractDTO contractDTO : allContracts) {
            if (checkInDate.after(contractDTO.getStartDate()) && checkInDate.before(contractDTO.getEndDate())) {
                matchingContracts.add(contractDTO);
            }
        }

        // Iterate through the matching contracts to fetch hotel and room type details
        for (ContractDTO matchingContract : matchingContracts) {
            int hotelId = matchingContract.getHotelId();
            HotelDTO hotelDTO = hotelService.getHotelWithRoomTypesById(hotelId);
            matchingContract.setHotel(hotelDTO);

            List<RoomTypeDTO> roomTypes = hotelDTO.getRoomTypes();
            matchingContract.setRoomTypes(roomTypes);
        }

        return matchingContracts;
    }


}
