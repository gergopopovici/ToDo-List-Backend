package edu.bbte.idde.pgim2289.spring.services;

import edu.bbte.idde.pgim2289.spring.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.spring.model.User;

import java.util.Collection;

public interface UserService {
    void create(User user) throws InvalidInputException;

    void delete(Long id) throws InvalidInputException;

    void update(User user) throws InvalidInputException;

    Collection<User> findAll();

    User findById(Long id);

    User findByUsername(String username);
}
