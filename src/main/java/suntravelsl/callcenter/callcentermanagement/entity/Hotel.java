package suntravelsl.callcenter.callcentermanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor // No-argument constructor is automatically generated
@AllArgsConstructor  // All-argument constructor is automatically generated
@Data // Getters, setters are automatically generated
@Entity //make Java class that corresponds to a database table
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private int hotelId;

    @NotBlank(message = "Hotel name cannot be empty")
    private String hotelName;

    @NotBlank(message = "Hotel location cannot be empty")
    private String location;

    @OneToMany(mappedBy = "hotel")
    private List<Contract> contracts;

    @OneToMany(mappedBy = "hotel")
    private List<RoomType> roomTypes;
}
