package com.crud.library.mapper;

import com.crud.library.dbService.TitleService;
import com.crud.library.domain.Title;
import com.crud.library.domain.Volume;
import com.crud.library.domain.VolumeDto;
import com.crud.library.exception.ValueNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VolumeMapper {
    private final TitleService titleService;

    @Autowired
    public VolumeMapper(TitleService titleService) {
        this.titleService = titleService;
    }

    public Volume mapToVolume(final VolumeDto volumeDto) throws ValueNotFoundException {
        return new Volume(
                volumeDto.getStatus(),
                titleService.getTitle(volumeDto.getTitleId()));
    }

    public VolumeDto mapToVolumeDto(final Volume volume) {
        return new VolumeDto(
                volume.getId(),
                volume.getStatus(),
                volume.getTitle().getId());
    }

    public List<VolumeDto> mapToVolumeDtoList(final List<Volume> volumes) {
        return volumes.stream()
                .map(this::mapToVolumeDto)
                .collect(Collectors.toList());
    }
}
