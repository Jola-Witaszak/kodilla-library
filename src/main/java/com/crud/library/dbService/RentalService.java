package com.crud.library.dbService;

import com.crud.library.domain.*;
import com.crud.library.exception.RentalAlreadyExistsException;
import com.crud.library.exception.RentalNotFoundException;
import com.crud.library.exception.UserNotExistsException;
import com.crud.library.exception.VolumeNotFoundException;
import com.crud.library.mapper.RentalMapper;
import com.crud.library.repository.RentalRepository;
import com.crud.library.repository.UserRepository;
import com.crud.library.repository.VolumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;
    private final UserRepository userRepository;
    private final VolumeRepository volumeRepository;

    public boolean checkIfRentalExists(long volumeId) {
        return rentalRepository.findByVolume_Id(volumeId).isPresent();
    }

    public RentalDto createRental(RentalDto rentalDto) throws RentalAlreadyExistsException, VolumeNotFoundException, UserNotExistsException {
        Volume volume = volumeRepository.findById(rentalDto.getVolumeId()).orElseThrow(() -> new VolumeNotFoundException("Volume with id " + rentalDto.getVolumeId() + " not exists."));
        User user = userRepository.findById(rentalDto.getUserId()).orElseThrow(() -> new UserNotExistsException("User with id " + rentalDto.getUserId() + " not exists."));

        if (checkIfRentalExists(volume.getId())) {
            throw new RentalAlreadyExistsException("Copy of id " + volume.getId() + " has already been loaned");
        }

        Rental rental = rentalMapper.mapToRental(rentalDto);

        user.getRentals().add(rental);
        volume.getRentals().add(rental);
        volume.setStatus(Status.RENTED);

        return rentalMapper.mapToRentalDto(rentalRepository.save(rental));
    }

    public void deleteRental(long volumeId) throws VolumeNotFoundException, RentalNotFoundException {
        Volume findVolume = volumeRepository.findById(volumeId).orElseThrow(()-> new VolumeNotFoundException("Copy of id " + volumeId + " not exists."));

        Rental rentalToReturn = findVolume.getRentals().stream()
                .filter(rental -> rental.getVolume().getId().equals(findVolume.getId()))
                .findFirst().orElseThrow(() -> new RentalNotFoundException("Copy of id " + volumeId + " was not rented."));

        findVolume.getRentals().remove(rentalToReturn);
        findVolume.setStatus(Status.ACCESSIBLE);

        User user = rentalToReturn.getUser();
        user.getRentals().remove(rentalToReturn);

        rentalRepository.deleteById(rentalToReturn.getId());
    }

    public List<RentalDto> getAllRentals() {
        return rentalMapper.mapToRentalDtoList(rentalRepository.findAll());
    }

    public RentalDto get(long rentalId) throws RentalNotFoundException {
        Rental foundRental =  rentalRepository.findById(rentalId).orElseThrow(() -> new RentalNotFoundException("Rental with id: " + rentalId + " not exists"));
        return rentalMapper.mapToRentalDto(foundRental);
    }
}
