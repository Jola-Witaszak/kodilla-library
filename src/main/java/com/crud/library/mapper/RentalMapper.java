package com.crud.library.mapper;

import com.crud.library.domain.Rental;
import com.crud.library.domain.RentalDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalMapper {

    public RentalDto mapToRentalDto(final Rental rental) {
        return new RentalDto(rental.getId(),
                rental.getRentalDate(),
                rental.getReturnDate(),
                rental.getVolume().getId(),
                rental.getUser().getId());
    }


    public List<RentalDto> mapToRentalDtoList(final List<Rental> rentals) {
        return  rentals.stream()
                .map(this::mapToRentalDto)
                .collect(Collectors.toList());
    }
}
