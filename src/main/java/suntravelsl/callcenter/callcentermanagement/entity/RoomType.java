package suntravelsl.callcenter.callcentermanagement.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private int roomTypeId;

    @NotBlank(message = "Room Type name cannot be empty")
    private String roomTypeName;

    @NotNull(message = "Max adults cannot be null")
    @Positive(message = "No. of Rooms should be greater than zero")
    private int maxAdults;

    @NotNull(message = "No of Rooms cannot be null")
    @Positive(message = "No. of Rooms should be greater than zero")
    private int numberOfRooms;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price should be greater than zero")
    private double price;

    @ManyToOne
    @JoinColumn(name= "hotelId")
    private Hotel hotel;

}
