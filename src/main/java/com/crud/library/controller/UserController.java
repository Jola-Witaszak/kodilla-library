package com.crud.library.controller;

import com.crud.library.dbService.UserService;
import com.crud.library.domain.UserDto;
import com.crud.library.exception.UserAlreadyExistsException;
import com.crud.library.exception.UserNotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/user")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDto createUser(@RequestBody UserDto userDto) throws UserAlreadyExistsException {
        return userService.createUser(userDto);
    }

    @GetMapping(value = "get")
    public UserDto getUser(@RequestParam long userId) throws UserNotExistsException {
        return userService.getUser(userId);
    }

    @GetMapping(value = "getAll")
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @DeleteMapping("delete")
    public void deleteUser(@RequestParam long userId) throws UserNotExistsException {
        userService.deleteUser(userId);
    }
}
