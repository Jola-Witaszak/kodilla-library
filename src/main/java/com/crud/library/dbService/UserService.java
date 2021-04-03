package com.crud.library.dbService;

import com.crud.library.domain.User;
import com.crud.library.domain.UserDto;
import com.crud.library.exception.UserAlreadyExistsException;
import com.crud.library.exception.UserNotExistsException;
import com.crud.library.mapper.UserMapper;
import com.crud.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto createUser(UserDto userDto) throws UserAlreadyExistsException {
        User user = userMapper.mapToUser(userDto);

        Optional<User> optionalUser = userRepository.findByLastName(user.getLastName()).stream()
                .filter(n -> n.getEmail().equals(user.getEmail()))
                .filter(u -> u.getFirstName().equals(user.getFirstName()))
                .findFirst();

        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistsException("This user already exists in the database.");
        }
        userRepository.save(user);
        return userMapper.mapToUserDto(user);
    }

    public UserDto getUser(long userId) throws UserNotExistsException {
         return userMapper.mapToUserDto(userRepository.findById(userId).orElseThrow(() -> new UserNotExistsException("User with id " + userId + " not exists.")));
    }

    public List<UserDto> getUsers() {
        return userMapper.mapToUsersDtoList(userRepository.findAll());
    }

    public void deleteUser(long userId) throws UserNotExistsException {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new UserNotExistsException("User with id " + userId + " not exists.");
        }
    }
}
