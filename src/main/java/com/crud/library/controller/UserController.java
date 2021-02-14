package com.crud.library.controller;

import com.crud.library.dbService.UserService;
import com.crud.library.exception.ValueAlreadyExistsException;
import com.crud.library.exception.ValueNotFoundException;
import com.crud.library.domain.User;
import com.crud.library.domain.UserDto;
import com.crud.library.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/user")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping(value = "createUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDto createUser(@RequestBody UserDto userDto) throws ValueAlreadyExistsException {
        User createdUser = userMapper.mapToUser(userDto);
        return userMapper.mapToUserDto(userService.createUser(createdUser));
    }

    @GetMapping(value = "getUser")
    public UserDto getUser(@RequestParam long userId) throws ValueNotFoundException {
        return userMapper.mapToUserDto(userService.getUser(userId));
    }

    @GetMapping(value = "getUsers")
    public List<UserDto> getUsers() {
        List<User> users = userService.getUsers();
        return userMapper.mapToUsersDtoList(users);
    }

    @DeleteMapping("library/deleteUser")
    public void deleteUser(@RequestParam long userId) throws ValueNotFoundException {
        userService.deleteUser(userId);
    }
}
