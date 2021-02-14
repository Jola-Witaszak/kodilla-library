package com.crud.library.controller;

import com.crud.library.exception.ValueAlreadyExistsException;
import com.crud.library.exception.ValueNotFoundException;
import com.crud.library.dbService.VolumeService;
import com.crud.library.domain.*;
import com.crud.library.mapper.TitleMapper;
import com.crud.library.mapper.VolumeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library/volume")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class VolumeController {
    private final VolumeService volumeService;
    private final VolumeMapper volumeMapper;
    private final TitleMapper titleMapper;

    @PostMapping(value = "createVolume", consumes = MediaType.APPLICATION_JSON_VALUE)
    public VolumeDto createVolume(@RequestBody VolumeDto volumeDto) throws ValueNotFoundException {
        Volume addedVolume = volumeMapper.mapToVolume(volumeDto);
        return volumeMapper.mapToVolumeDto(volumeService.createVolume(addedVolume));
    }

    @GetMapping(value = "getVolume")
    public VolumeDto getVolume(@RequestParam long volumeId) throws ValueNotFoundException {
        Volume volume = volumeService.getVolume(volumeId);
        return volumeMapper.mapToVolumeDto(volume);
    }

    @PutMapping(value = "updateVolumeStatus")
    public VolumeDto updateVolumeStatus(@RequestParam long volumeId, @RequestParam Status status) throws ValueNotFoundException, ValueAlreadyExistsException {
        Volume updatedVolume = volumeService.updateVolumeStatus(volumeId, status);
        return volumeMapper.mapToVolumeDto(updatedVolume);
    }

    @DeleteMapping(value = "deleteVolume")
    public void deleteVolume(@RequestParam long volumeId) throws ValueNotFoundException {
        volumeService.deleteVolume(volumeId);
    }
}
