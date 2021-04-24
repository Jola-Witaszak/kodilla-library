package com.crud.library.controller;

import com.crud.library.dbService.TitleService;
import com.crud.library.domain.TitleDto;
import com.crud.library.exception.TitleAlreadyExistsException;
import com.crud.library.exception.TitleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/title")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TitleController {

    private final TitleService titleService;

    @PostMapping
    public TitleDto createTitle(@RequestBody TitleDto titleDto) throws TitleAlreadyExistsException {
        return titleService.createTitle(titleDto);
    }

    @GetMapping
    public List<TitleDto> getAllTitles() {
        return titleService.getAllTitles();
    }

    @GetMapping("/one/{id}")
    public TitleDto getTitle(@PathVariable("id") long id) throws TitleNotFoundException {
        return titleService.getTitle(id);
    }

    @GetMapping("/{titleId}")
    public long getAccessibleCopies(@PathVariable("titleId") long titleId) throws TitleNotFoundException {
        return titleService.getAccessibleVolumes(titleId);
    }
}
