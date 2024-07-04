package suntravelsl.callcenter.callcentermanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import suntravelsl.callcenter.callcentermanagement.entity.RoomType;

import java.util.List;

public interface RoomTypeRepo extends JpaRepository<RoomType, Integer> {

    List<RoomType> findByHotel_HotelId(int hotelId);  //find room type with the hotelId
}
