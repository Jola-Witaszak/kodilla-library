package com.crud.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "rentals")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Rental {
    @Id
    @GeneratedValue
    private Long id;

    private final LocalDate rentalDate = LocalDate.now();

    private final LocalDate returnDate = LocalDate.now().plusDays(14);

    @ManyToOne()
    @JoinColumn(name = "volume_id")
    @NotNull
    private Volume volume;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

}
