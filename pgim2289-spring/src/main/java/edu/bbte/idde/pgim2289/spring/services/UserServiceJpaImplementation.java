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

        if (userJpa.findByUsername(user.getUsername()) != null) {
            throw new InvalidInputException("User with username " + user.getUsername()
                    + " already exists.");
        }

        if (userJpa.findByEmail(user.getEmail()) != null) {
            throw new InvalidInputException("User with email " + user.getEmail()
                    + " already exists.");
        }
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
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new InvalidInputException("Invalid input: email cannot be "
                    + "empty and it must be valid.");
        }
    }

    private void validatePhoneNumber(String phoneNumber) throws InvalidInputException {
        if (phoneNumber == null || !phoneNumber.matches("\\d{11}")) {
            throw new InvalidInputException("Invalid input: phone number must "
                    + "be 11 digits long and contain only numbers.");
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userJpa.save(user);
    }

    @Override
    public void delete(Long adminId, Long id) throws InvalidInputException {
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
        return userJpa.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User not found with id: " + id));
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