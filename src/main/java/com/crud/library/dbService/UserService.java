package com.crud.library.dbService;

import com.crud.library.domain.User;
import com.crud.library.exception.ValueAlreadyExistsException;
import com.crud.library.exception.ValueNotFoundException;
import com.crud.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(User user) throws ValueAlreadyExistsException {
        Optional<User> optionalUser = userRepository.findByLastName(user.getLastName()).stream()
                .filter(n -> n.getEMail().equals(user.getEMail()))
                .filter(u -> u.getFirstName().equals(user.getFirstName()))
                .findAny();

        if (optionalUser.isPresent()) {
            throw new ValueAlreadyExistsException("This user already exists");
        }

        return userRepository.save(user);
    }

    public User getUser(long userId) throws ValueNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElseThrow(() -> new ValueNotFoundException("User not exists"));
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(long userId) throws ValueNotFoundException {
        User user = getUser(userId);
        userRepository.deleteById(user.getId());
    }
}
