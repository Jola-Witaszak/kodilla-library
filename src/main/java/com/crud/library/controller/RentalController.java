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

    @PostMapping (value = "create")
    public RentalDto createRental(@RequestParam long volumeId, @RequestParam long userId) throws RentalAlreadyExistsException, UserNotExistsException, VolumeNotFoundException {
        return rentalService.createRental(volumeId, userId);
    }

    @GetMapping("get")
    public List<RentalDto> getRentals() {
        return rentalService.getAllRentals();
    }

    @DeleteMapping(value = "delete")
    public void deleteRental(@RequestParam long volumeId) throws RentalNotFoundException, VolumeNotFoundException {
        rentalService.deleteRental(volumeId);
    }
}
