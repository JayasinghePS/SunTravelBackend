package suntravelsl.callcenter.callcentermanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int contractId;

    @NotNull(message = "Start date cannot be null")
    @Positive(message = "Price should be greater than zero")
    private double markupPercentage;

    @NotNull(message = "Start date cannot be null")
    @FutureOrPresent(message = "Start date should be in present or future")
    private Date startDate;

    @NotNull(message = "End date cannot be null")
    @Future(message = "End date should be in future")
    private Date endDate;

    @ManyToOne
    @JoinColumn(name= "hotelId")
    private Hotel hotel;

}
