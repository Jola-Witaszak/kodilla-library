package com.crud.library.dbService;

import com.crud.library.domain.Status;
import com.crud.library.domain.Title;
import com.crud.library.domain.Volume;
import com.crud.library.exception.ValueAlreadyExistsException;
import com.crud.library.exception.ValueNotFoundException;
import com.crud.library.repository.VolumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VolumeService {

    private final VolumeRepository volumeRepository;
    private final TitleService titleService;

    public Volume createVolume(Volume volume) throws ValueNotFoundException {
        Title foundTitle = titleService.getTitle(volume.getTitle().getId());
        foundTitle.getVolumes().add(volume);
        return volumeRepository.save(volume);
    }

    public Volume getVolume(long volumeId) throws ValueNotFoundException {

        Optional<Volume> optionalVolume = volumeRepository.findById(volumeId);
        return optionalVolume.orElseThrow(() -> new ValueNotFoundException("Volume not exists in the database"));
    }

    public Volume updateVolumeStatus(long volumeId, Status status) throws ValueNotFoundException, ValueAlreadyExistsException {
        Volume findVolume = getVolume(volumeId);

        if (findVolume.getStatus().equals(status)) {
            throw new ValueAlreadyExistsException("This volume has already status  " + status);
        }
        findVolume.setStatus(status);
        volumeRepository.save(findVolume);
        return findVolume;
    }

    public void deleteVolume(long volumeId) throws ValueNotFoundException {
        Volume volumeToDelete = getVolume(volumeId);
        volumeRepository.deleteById(volumeToDelete.getId());
    }
}
