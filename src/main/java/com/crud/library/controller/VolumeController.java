package com.crud.library.controller;

import com.crud.library.dbService.VolumeService;
import com.crud.library.domain.VolumeDto;
import com.crud.library.exception.DuplicateStatusException;
import com.crud.library.exception.TitleNotFoundException;
import com.crud.library.exception.VolumeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library/volume")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class VolumeController {
    private final VolumeService volumeService;

    @PostMapping
    public VolumeDto create(@RequestBody VolumeDto volumeDto) throws TitleNotFoundException {
        return volumeService.createVolume(volumeDto);
    }

    @GetMapping(value = "/{volumeId}")
    public VolumeDto get(@PathVariable long volumeId) throws VolumeNotFoundException {
        return volumeService.getVolume(volumeId);
    }

    @PutMapping
    public VolumeDto updateStatus(@RequestBody VolumeDto volumeDto) throws DuplicateStatusException, VolumeNotFoundException, TitleNotFoundException {
        return volumeService.updateVolumeStatus(volumeDto);
    }

    @DeleteMapping(value = "/{volumeId}")
    public void delete(@PathVariable long volumeId) throws VolumeNotFoundException {
        volumeService.deleteVolume(volumeId);
    }
}
