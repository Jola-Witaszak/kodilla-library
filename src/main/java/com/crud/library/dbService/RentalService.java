package com.crud.library.dbService;

import com.crud.library.domain.*;
import com.crud.library.exception.*;
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

    public RentalDto createRental(long volumeId, long userId) throws RentalAlreadyExistsException, VolumeNotFoundException, UserNotExistsException {
        Volume volume = volumeRepository.findById(volumeId).orElseThrow(() -> new VolumeNotFoundException("Volume with id " + volumeId + " not exists."));
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotExistsException("User with id " + userId + " not exists."));

        if (checkIfRentalExists(volumeId)) {
            throw new RentalAlreadyExistsException("Copy of id " + volumeId + " has already been loaned");
        }

        Rental rental = new Rental();
        rental.setVolume(volume);
        rental.setUser(user);

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
}
