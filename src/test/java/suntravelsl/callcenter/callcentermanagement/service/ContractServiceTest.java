package suntravelsl.callcenter.callcentermanagement.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import suntravelsl.callcenter.callcentermanagement.CallcentermanagementApplication;
import suntravelsl.callcenter.callcentermanagement.exception.NotFoundException;
import suntravelsl.callcenter.callcentermanagement.repo.ContractRepo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {CallcentermanagementApplication.class})
class ContractServiceTest {
    @Mock
    private ContractRepo contractRepo;

    @InjectMocks
    private ContractService contractService;

    @Test
    void testDeleteContractById_Exists() {
        int contractId = 1;

        when(contractRepo.existsById(contractId)).thenReturn(true);

        boolean result = contractService.deleteContractById(contractId);

        assertTrue(result);
        verify(contractRepo, times(1)).deleteById(contractId);
    }

    @Test
    void testDeleteContractById_NotExists() {
        int contractId = 1;

        when(contractRepo.existsById(contractId)).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                contractService.deleteContractById(contractId));

        assertEquals("Contract not found with id: " + contractId, exception.getMessage());
        verify(contractRepo, never()).deleteById(contractId);
    }
}