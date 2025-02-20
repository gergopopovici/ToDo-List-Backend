package edu.bbte.idde.pgim2289.spring.presentation;

import edu.bbte.idde.pgim2289.spring.dto.RequestUserDTO;
import edu.bbte.idde.pgim2289.spring.dto.ResponseUserDTO;
import edu.bbte.idde.pgim2289.spring.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.spring.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.spring.mapper.UserMapper;
import edu.bbte.idde.pgim2289.spring.model.User;
import edu.bbte.idde.pgim2289.spring.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Profile("jpa")
@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {
    private final UserService userService;
    @Autowired
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public Collection<ResponseUserDTO> getAllUsers() {
        return userService.findAll().stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseUserDTO getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return userMapper.toDTO(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseUserDTO createUser(@RequestBody @Valid RequestUserDTO user) throws InvalidInputException {
        User newUser = userMapper.toEntity(user);
        userService.create(newUser);
        return userMapper.toDTO(newUser);
    }

    @PutMapping("/{id}")
    public ResponseUserDTO updateUser(@PathVariable Long id, @RequestBody @Valid RequestUserDTO user)
            throws EntityNotFoundException, InvalidInputException {
        User updatedUser = userMapper.toEntity(user);
        updatedUser.setId(id);
        userService.update(updatedUser);
        return userMapper.toDTO(updatedUser);
    }

    @DeleteMapping("/{adminId}/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long adminId, @PathVariable Long id) throws EntityNotFoundException {
        User user = userService.findById(adminId);
        log.info("User {} is trying to delete user with id {}", user.getUsername(), id);
        log.info("Admin id: {}", user.getAdmin());
        if (Boolean.TRUE.equals(user.getAdmin())) {
            log.info("User {} is authorized to delete user with id {}", user.getUsername(), id);
            userService.delete(adminId, id);
        } else {
            throw new AccessDeniedException("You are not authorized to delete users.");
        }
    }


}
