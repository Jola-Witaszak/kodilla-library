package com.crud.library.dbService;

import com.crud.library.domain.Status;
import com.crud.library.domain.Title;
import com.crud.library.domain.Volume;
import com.crud.library.exception.ValueAlreadyExistsException;
import com.crud.library.exception.ValueNotFoundException;
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

    public Title createTitle(Title title) throws ValueAlreadyExistsException {
        Optional<Title> optionalTitle = titleRepository.findByAuthor(title.getAuthor()).stream()
                .filter(t -> t.getTitle().equals(title.getTitle()))
                .filter(r -> r.getPublicationYear().equals(title.getPublicationYear()))
                .findFirst();

        if (optionalTitle.isPresent()) {
            throw new ValueAlreadyExistsException("Title already exists");
        }
            Volume volume = new Volume();
            volume.setStatus(Status.ACCESSIBLE);
            volume.setTitle(title);
            title.getVolumes().add(volume);

        return titleRepository.save(title);
    }

    public Title getTitle(long titleId) throws ValueNotFoundException {
        Optional<Title> optionalTitle = titleRepository.findById(titleId);
        return optionalTitle.orElseThrow(() -> new ValueNotFoundException("Title with id " + titleId + " not exists"));
    }

    public List<Title> getAllTitles() {
        return titleRepository.findAll();
    }

    public int getAccessibleVolumes(long titleId) throws ValueNotFoundException {
        Title findTitle = getTitle(titleId);
        List<Volume> volumes = findTitle.getVolumes().stream()
                            .filter(volume -> volume.getStatus().equals(Status.ACCESSIBLE))
                            .collect(Collectors.toList());
        return volumes.size();
    }
}
