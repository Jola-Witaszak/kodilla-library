package com.crud.library.dbService;

import com.crud.library.domain.Status;
import com.crud.library.domain.Title;
import com.crud.library.domain.Volume;
import com.crud.library.domain.VolumeDto;
import com.crud.library.exception.DuplicateStatusException;
import com.crud.library.exception.TitleNotFoundException;
import com.crud.library.exception.VolumeNotFoundException;
import com.crud.library.mapper.VolumeMapper;
import com.crud.library.repository.TitleRepository;
import com.crud.library.repository.VolumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VolumeService {

    private final VolumeRepository volumeRepository;
    private final VolumeMapper volumeMapper;
    private final TitleRepository titleRepository;

    public VolumeDto createVolume(VolumeDto volumeDto) throws TitleNotFoundException {
        Volume volume = volumeMapper.mapToVolume(volumeDto);
        Title foundTitle = titleRepository.findById(volume.getTitle().getId()).orElseThrow(() -> new TitleNotFoundException("You cannot create a copy until the title exists in the database"));
        foundTitle.getVolumes().add(volume);
        return volumeMapper.mapToVolumeDto(volumeRepository.save(volume));
    }

    public VolumeDto getVolume(long volumeId) throws VolumeNotFoundException {
        return volumeMapper.mapToVolumeDto(volumeRepository.findById(volumeId).orElseThrow(() -> new VolumeNotFoundException("Volume with id " + volumeId + " not exists.")));
    }

    public VolumeDto updateVolumeStatus(long volumeId, Status status) throws DuplicateStatusException, VolumeNotFoundException, TitleNotFoundException {
        Volume foundVolume = volumeMapper.mapToVolume(getVolume(volumeId));

        if (foundVolume.getStatus().equals(status)) {
            throw new DuplicateStatusException("You cannot change the status " + status + " to " + status);
        }
        foundVolume.setStatus(status);
        return volumeMapper.mapToVolumeDto(volumeRepository.save(foundVolume));
    }

    public void deleteVolume(long volumeId) throws VolumeNotFoundException {
        Volume volumeToDelete = volumeRepository.findById(volumeId).orElseThrow(() -> new VolumeNotFoundException("Volume with id " + volumeId + " not exists."));
        volumeRepository.deleteById(volumeToDelete.getId());
    }
}
