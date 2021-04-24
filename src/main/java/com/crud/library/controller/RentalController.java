package com.crud.library.controller;

import com.crud.library.dbService.RentalService;
import com.crud.library.domain.RentalDto;
import com.crud.library.exception.RentalAlreadyExistsException;
import com.crud.library.exception.RentalNotFoundException;
import com.crud.library.exception.UserNotExistsException;
import com.crud.library.exception.VolumeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/rental")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;

    @PostMapping
    public RentalDto createRental(@RequestBody RentalDto rentalDto) throws RentalAlreadyExistsException, UserNotExistsException, VolumeNotFoundException {
        return rentalService.createRental(rentalDto);
    }

    @GetMapping
    public List<RentalDto> getAll() {
        return rentalService.getAllRentals();
    }

    @GetMapping("/{id}")
    public RentalDto get(@PathVariable("id") long id) throws RentalNotFoundException {
        return rentalService.get(id);
    }

    @DeleteMapping("/{volumeId}")
    public void deleteRental(@PathVariable("volumeId") long volumeId) throws RentalNotFoundException, VolumeNotFoundException {
        rentalService.deleteRental(volumeId);
    }
}
