package com.crud.library.controller;

import com.crud.library.dbService.RentalService;
import com.crud.library.exception.ValueAlreadyExistsException;
import com.crud.library.exception.ValueNotFoundException;
import com.crud.library.domain.Rental;
import com.crud.library.domain.RentalDto;
import com.crud.library.mapper.RentalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/rental")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;
    private final RentalMapper rentalMapper;

    @PostMapping(value = "createRental")
    public RentalDto createRental(@RequestParam long volumeId, @RequestParam long userId) throws ValueNotFoundException, ValueAlreadyExistsException {
        return rentalMapper.mapToRentalDto(rentalService.createRental(volumeId, userId));
    }

    @GetMapping("getRentals")
    public List<RentalDto> getRentals() {
        List<Rental> rentals = rentalService.getAllRentals();
        return rentalMapper.mapToRentalDtoList(rentals);
    }

    @DeleteMapping(value = "returnRental")
    public void returnRental(@RequestParam long volumeId) throws ValueNotFoundException {
        rentalService.returnRental(volumeId);
    }
}
