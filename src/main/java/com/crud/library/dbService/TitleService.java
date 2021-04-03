package com.crud.library.dbService;

import com.crud.library.domain.Status;
import com.crud.library.domain.Title;
import com.crud.library.domain.TitleDto;
import com.crud.library.domain.Volume;
import com.crud.library.exception.TitleAlreadyExistsException;
import com.crud.library.exception.TitleNotFoundException;
import com.crud.library.mapper.TitleMapper;
import com.crud.library.repository.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TitleService {
    private final TitleRepository titleRepository;
    private final TitleMapper titleMapper;

    public TitleDto createTitle(TitleDto titleDto) throws TitleAlreadyExistsException {
        Title title = titleMapper.mapToTitle(titleDto);

        Optional<Title> optionalTitle = titleRepository.findByAuthor(title.getAuthor()).stream()
                .filter(t -> t.getTitle().equals(title.getTitle()))
                .filter(r -> r.getPublicationYear().equals(title.getPublicationYear()))
                .findFirst();

        if (optionalTitle.isPresent()) {
            throw new TitleAlreadyExistsException("This title already exists in the database.");
        }
            Volume volume = new Volume();
            volume.setStatus(Status.ACCESSIBLE);
            volume.setTitle(title);
            title.getVolumes().add(volume);

            titleRepository.save(title);

        return titleMapper.mapToTitleDto(title);
    }

    public TitleDto getTitle(long titleId) throws TitleNotFoundException {
        return titleMapper.mapToTitleDto(titleRepository.findById(titleId).orElseThrow(() -> new TitleNotFoundException("Title with id " + titleId + " not exists in the database.")));
    }

    public List<TitleDto> getAllTitles() {
        return titleMapper.mapToTitleDtoList(titleRepository.findAll());
    }

    public int getAccessibleVolumes(long titleId) throws TitleNotFoundException {
        Title foundTitle = titleMapper.mapToTitle(getTitle(titleId));

        List<Volume> volumes = foundTitle.getVolumes().stream()
                            .filter(volume -> volume.getStatus().equals(Status.ACCESSIBLE))
                            .collect(Collectors.toList());
        return volumes.size();
    }
}
