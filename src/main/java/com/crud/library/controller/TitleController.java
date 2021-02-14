package com.crud.library.controller;

import com.crud.library.dbService.TitleService;
import com.crud.library.exception.ValueAlreadyExistsException;
import com.crud.library.exception.ValueNotFoundException;
import com.crud.library.domain.Title;
import com.crud.library.domain.TitleDto;
import com.crud.library.mapper.TitleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/title")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TitleController {

    private final TitleService titleService;
    private final TitleMapper titleMapper;

    @PostMapping(value = "createTitle", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TitleDto createTitle(@RequestBody TitleDto titleDto) throws ValueAlreadyExistsException {
        Title addedTitle = titleMapper.mapToTitle(titleDto);
        return titleMapper.mapToTitleDto(titleService.createTitle(addedTitle));
    }

    @GetMapping(value = "getAllTitles")
    public List<TitleDto> getAllTitles() {
        List<Title> titles = titleService.getAllTitles();
        return titleMapper.mapToTitleDtoList(titles);
    }

    @GetMapping(value = "getTitle")
    public TitleDto getTitle(@RequestParam long titleId) throws ValueNotFoundException {
        return titleMapper.mapToTitleDto(titleService.getTitle(titleId));
    }

    @GetMapping(value = "getAccessibleCopies")
    public int getAccessibleCopies (@RequestParam long titleId) throws ValueNotFoundException {
        return titleService.getAccessibleVolumes(titleId);
    }
}
