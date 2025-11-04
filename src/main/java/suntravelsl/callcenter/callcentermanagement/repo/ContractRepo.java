package suntravelsl.callcenter.callcentermanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import suntravelsl.callcenter.callcentermanagement.entity.Contract;

import java.util.List;

//extend JpaRepository, which gives all default methods like save(), findAll(), findById(), delete(), etc.
public interface ContractRepo extends JpaRepository<Contract, Integer> {
    // Custom finder method – JPA auto-generates query:
    // SELECT * FROM contract WHERE hotel.hotelId = :hotelId
    List<Contract> findByHotelHotelId(int hotelId);  //find contract with the hotelId
}
