package com.crud.library.mapper;

import com.crud.library.domain.User;
import com.crud.library.domain.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {
    public UserDto mapToUserDto(final User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEMail(),
                user.getAccountCreated());
    }

    public User mapToUser(final UserDto userDto) {
        return new User(
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEMail(),
                userDto.getAccountCreated());
    }

    public List<UserDto> mapToUsersDtoList(List<User> usersList) {
        return usersList.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }
}
