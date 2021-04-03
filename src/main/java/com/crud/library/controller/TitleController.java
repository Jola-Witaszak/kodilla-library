package com.crud.library.controller;

import com.crud.library.dbService.TitleService;
import com.crud.library.exception.TitleAlreadyExistsException;
import com.crud.library.domain.TitleDto;
import com.crud.library.exception.TitleNotFoundException;
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

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TitleDto createTitle(@RequestBody TitleDto titleDto) throws TitleAlreadyExistsException {
        return titleService.createTitle(titleDto);
    }

    @GetMapping(value = "getAll")
    public List<TitleDto> getAllTitles() {
        return titleService.getAllTitles();
    }

    @GetMapping(value = "get")
    public TitleDto getTitle(@RequestParam long titleId) throws TitleNotFoundException {
        return titleService.getTitle(titleId);
    }

    @GetMapping(value = "getAccessible")
    public int getAccessibleCopies (@RequestParam long titleId) throws TitleNotFoundException {
        return titleService.getAccessibleVolumes(titleId);
    }
}
