package edu.bbte.idde.pgim2289.spring.services;

import edu.bbte.idde.pgim2289.spring.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.spring.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.spring.model.User;
import edu.bbte.idde.pgim2289.spring.repository.UserJpa;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Profile("jpa")
public class UserServiceJpaImplementation implements UserService {
    private final UserJpa userJpa;
    private final PasswordEncoder passwordEncoder;

    public UserServiceJpaImplementation(UserJpa userJpa) {
        this.userJpa = userJpa;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    private void validateUserInput(User user) throws InvalidInputException {
        validateUsername(user.getUsername());
        validatePassword(user.getPassword());
        validateEmail(user.getEmail());
        validatePhoneNumber(user.getPhoneNumber());
        validateAdmin(user.getAdmin());
    }

    private void validateUsername(String username) throws InvalidInputException {
        if (username == null || username.isBlank()) {
            throw new InvalidInputException("Invalid input: username cannot be empty.");
        }
    }

    private void validatePassword(String password) throws InvalidInputException {
        if (password == null || password.isBlank()) {
            throw new InvalidInputException("Invalid input: password cannot be empty.");
        }
    }

    private void validateEmail(String email) throws InvalidInputException {
        if (email == null || email.isBlank()) {
            throw new InvalidInputException("Invalid input: email cannot be empty.");
        }
    }

    private void validatePhoneNumber(String phoneNumber) throws InvalidInputException {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new InvalidInputException("Invalid input: phone number cannot be empty.");
        }
    }

    private void validateAdmin(Boolean admin) throws InvalidInputException {
        if (admin == null) {
            throw new InvalidInputException("Invalid input: admin cannot be empty.");
        }
    }

    @Override
    public void create(User user) throws InvalidInputException {
        validateUserInput(user);
        if (userJpa.findByUsername(user.getUsername()) != null) {
            throw new InvalidInputException("User with username " + user.getUsername() + " already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userJpa.save(user);
    }

    @Override
    public void delete(Long id) throws InvalidInputException {
        userJpa.deleteById(id);
    }

    @Override
    public void update(User user) throws InvalidInputException {
        validateUserInput(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userJpa.save(user);
    }

    @Override
    public User findById(Long id) {
        return userJpa.findById(id).orElseThrow(() -> new EntityNotFoundException("ToDo not found with id: " + id));
    }

    @Override
    public Collection<User> findAll() {
        return userJpa.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userJpa.findByUsername(username);
    }
}
