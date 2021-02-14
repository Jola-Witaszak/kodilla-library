package com.crud.library.dbService;

import com.crud.library.domain.Rental;
import com.crud.library.domain.Status;
import com.crud.library.domain.User;
import com.crud.library.domain.Volume;
import com.crud.library.exception.ValueAlreadyExistsException;
import com.crud.library.exception.ValueNotFoundException;
import com.crud.library.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserService userService;
    private final VolumeService volumeService;

    public boolean checkIfRentalExists(long volumeId) {
        Optional<Rental> optionalRental = rentalRepository.findByVolume_Id(volumeId);
        return optionalRental.isPresent();
    }

    public Rental createRental(long volumeId, long userId) throws ValueNotFoundException, ValueAlreadyExistsException {
        Volume volume = volumeService.getVolume(volumeId);
        User user = userService.getUser(userId);

        if (checkIfRentalExists(volumeId)) {
            throw new ValueAlreadyExistsException("Rental with volume id " + volumeId + " already exists");
        }

        Rental rental = new Rental();
        rental.setVolume(volume);
        rental.setUser(user);

        user.getRentals().add(rental);
        volume.getRentals().add(rental);
        volume.setStatus(Status.RENTED);

        return rentalRepository.save(rental);
    }

    public void returnRental(long volumeId) throws ValueNotFoundException {
        Volume findVolume = volumeService.getVolume(volumeId);

        Rental rentalToReturn = findVolume.getRentals().stream()
                .filter(rental -> rental.getVolume().getId().equals(findVolume.getId()))
                .findFirst().orElseThrow(() -> new ValueNotFoundException("Rental with volume id " + volumeId + " not exists"));

        findVolume.getRentals().remove(rentalToReturn);
        findVolume.setStatus(Status.ACCESSIBLE);

        User user = rentalToReturn.getUser();
        user.getRentals().remove(rentalToReturn);

        rentalRepository.deleteById(rentalToReturn.getId());
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }
}
