package com.crud.library.controller;

import com.crud.library.dbService.VolumeService;
import com.crud.library.domain.*;
import com.crud.library.exception.DuplicateStatusException;
import com.crud.library.exception.TitleNotFoundException;
import com.crud.library.exception.VolumeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library/volume")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class VolumeController {
    private final VolumeService volumeService;

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public VolumeDto createVolume(@RequestBody VolumeDto volumeDto) throws TitleNotFoundException {
        return volumeService.createVolume(volumeDto);
    }

    @GetMapping(value = "get")
    public VolumeDto getVolume(@RequestParam long volumeId) throws VolumeNotFoundException {
        return volumeService.getVolume(volumeId);
    }

    @PutMapping(value = "updateStatus")
    public VolumeDto updateVolumeStatus(@RequestParam long volumeId, @RequestParam Status status) throws DuplicateStatusException, VolumeNotFoundException, TitleNotFoundException {
        return volumeService.updateVolumeStatus(volumeId, status);
    }

    @DeleteMapping(value = "delete")
    public void deleteVolume(@RequestParam long volumeId) throws VolumeNotFoundException {
        volumeService.deleteVolume(volumeId);
    }
}
