package com.crud.library.dbService;

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

import static com.crud.library.domain.Status.ACCESSIBLE;

@Service
@RequiredArgsConstructor
public class TitleService {
    private final TitleRepository titleRepository;
    private final TitleMapper titleMapper;

    public boolean checkIfTitleExists(Title title) throws TitleAlreadyExistsException {
        Optional<Title> optionalTitle = titleRepository.findByAuthor(title.getAuthor()).stream()
                .filter(t -> t.getTitle().equals(title.getTitle()))
                .filter(r -> r.getPublicationYear().equals(title.getPublicationYear()))
                .findFirst();
        if (optionalTitle.isEmpty()) {
            return false;
        } else {
            throw new TitleAlreadyExistsException("This title already exists in the database.");
        }
    }

    public TitleDto createTitle(TitleDto titleDto) throws TitleAlreadyExistsException {
        Title title = titleMapper.mapToTitle(titleDto);

        if (!checkIfTitleExists(title)) {
            Volume volume = new Volume();
            volume.setStatus(ACCESSIBLE);
            volume.setTitle(title);
            title.getVolumes().add(volume);
        }
        Title savedTitle = titleRepository.save(title);
        return titleMapper.mapToTitleDto(savedTitle);
    }

    public TitleDto getTitle(long titleId) throws TitleNotFoundException {
        Title title = titleRepository.findById(titleId).orElseThrow(() -> new TitleNotFoundException("Title with id " + titleId + " not exists in the database."));
        return titleMapper.mapToTitleDto(title);
    }

    public List<TitleDto> getAllTitles() {
        return titleMapper.mapToTitleDtoList(titleRepository.findAll());
    }

    public long getAccessibleVolumes(long titleId) throws TitleNotFoundException {
        Title foundTitle = titleMapper.mapToTitle(getTitle(titleId));

         return foundTitle.getVolumes().stream()
                            .filter(volume -> volume.getStatus().equals(ACCESSIBLE))
                            .count();
    }
}
