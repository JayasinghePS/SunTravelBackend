package suntravelsl.callcenter.callcentermanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import suntravelsl.callcenter.callcentermanagement.entity.Hotel;

// Provides CRUD methods for Hotel entity (save, find, delete, etc.)
public interface HotelRepo extends JpaRepository<Hotel, Integer> {
}
