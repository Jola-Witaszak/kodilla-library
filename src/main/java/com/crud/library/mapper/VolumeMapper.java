package com.crud.library.mapper;

import com.crud.library.domain.Volume;
import com.crud.library.domain.VolumeDto;
import com.crud.library.exception.TitleNotFoundException;
import com.crud.library.repository.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VolumeMapper {
    private final TitleRepository titleRepository;

    public Volume mapToVolume(final VolumeDto volumeDto) throws TitleNotFoundException {
        return new Volume(
                volumeDto.getId(),
                volumeDto.getStatus(),
                titleRepository.findById(volumeDto.getTitleId()).orElseThrow(() -> new TitleNotFoundException("Title not found")));
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
