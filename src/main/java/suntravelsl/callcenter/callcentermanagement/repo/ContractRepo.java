package suntravelsl.callcenter.callcentermanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import suntravelsl.callcenter.callcentermanagement.entity.Contract;

import java.util.List;

public interface ContractRepo extends JpaRepository<Contract, Integer> {
    List<Contract> findByHotelHotelId(int hotelId);  //find contract with the hotelId
}
