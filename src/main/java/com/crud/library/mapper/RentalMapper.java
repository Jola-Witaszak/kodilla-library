package com.crud.library.mapper;

import com.crud.library.domain.Rental;
import com.crud.library.domain.RentalDto;
import com.crud.library.exception.UserNotExistsException;
import com.crud.library.exception.VolumeNotFoundException;
import com.crud.library.repository.UserRepository;
import com.crud.library.repository.VolumeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalMapper {

    private final VolumeRepository volumeRepository;
    private final UserRepository userRepository;

    public RentalMapper(VolumeRepository volumeRepository, UserRepository userRepository) {
        this.volumeRepository = volumeRepository;
        this.userRepository = userRepository;
    }

    public Rental mapToRental(final RentalDto rentalDto) throws UserNotExistsException, VolumeNotFoundException {
        return new Rental(
                rentalDto.getId(),
                rentalDto.getRentalDate(),
                rentalDto.getReturnDate(),
                volumeRepository.findById(rentalDto.getVolumeId()).orElseThrow(() -> new VolumeNotFoundException("Volume not found")),
                userRepository.findById(rentalDto.getUserId()).orElseThrow(() -> new UserNotExistsException("User not exists")));

    }

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
