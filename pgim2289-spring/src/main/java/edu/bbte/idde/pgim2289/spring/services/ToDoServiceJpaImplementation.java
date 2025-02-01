package edu.bbte.idde.pgim2289.spring.services;

import edu.bbte.idde.pgim2289.spring.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.spring.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.spring.model.ToDo;
import edu.bbte.idde.pgim2289.spring.repository.UserJpa;
import edu.bbte.idde.pgim2289.spring.repository.repo.ToDoJpaRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
@Profile("jpa")
public class ToDoServiceJpaImplementation implements ToDoService {
    private final ToDoJpaRepo toDoDaoJpa;
    private final UserJpa userJpa;
    private final Logger logger = LoggerFactory.getLogger(ToDoServiceJpaImplementation.class);

    @Autowired
    public ToDoServiceJpaImplementation(ToDoJpaRepo toDoDaoJpa, UserJpa userJpa) {
        this.toDoDaoJpa = toDoDaoJpa;
        this.userJpa = userJpa;
    }


    @Override
    public void create(ToDo toDo) throws InvalidInputException {
        validateToDoInput(toDo);
        logger.info("Creating new ToDo: {}", toDo);
        if (!userJpa.existsById(toDo.getUserId())) {
            throw new InvalidInputException("Invalid input: User with the given ID does not exist.");
        }
        toDoDaoJpa.save(toDo);
    }

    private void validateToDoInput(ToDo toDo) throws InvalidInputException {
        validateUserId(toDo.getUserId());
        validateTitle(toDo.getTitle());
        validateDescription(toDo.getDescription());
        validateDate(toDo.getDate());
        validatePriority(toDo.getPriority());
    }

    private void validateUserId(Long userId) throws InvalidInputException {
        if (!userJpa.existsById(userId)) {
            throw new InvalidInputException("Invalid input: "
                    + "User with the given ID does not exist.");
        }
    }

    private void validateTitle(String title) throws InvalidInputException {
        if (title == null || title.isBlank()) {
            throw new InvalidInputException("Invalid input: title cannot be empty.");
        }
    }

    private void validateDescription(String description) throws InvalidInputException {
        if (description == null || description.isBlank()) {
            throw new InvalidInputException("Invalid input: description cannot be empty.");
        }
    }

    private void validateDate(Date date) throws InvalidInputException {
        if (date == null) {
            throw new InvalidInputException("Invalid input: due date cannot be empty.");
        }
    }

    private void validatePriority(Integer priority) throws InvalidInputException {
        if (priority == null || priority < 1 || priority > 3) {
            throw new InvalidInputException("Invalid input: priority must be between 1 and 3.");
        }
    }

    @Override
    public Collection<ToDo> findAll() {
        return toDoDaoJpa.findAll();
    }

    @Override
    public void delete(Long id, Long userId) throws EntityNotFoundException {
        ToDo toDo = findById(id);
        if (!toDo.getUserId().equals(userId)) {
            throw new InvalidInputException("Invalid operation: Only the owner can delete this ToDo.");
        }
        toDoDaoJpa.deleteById(id);
    }

    @Override
    public void update(ToDo toDo) throws EntityNotFoundException, InvalidInputException {
        validateToDoInput(toDo);
        ToDo existingToDo = findById(toDo.getId());
        if (!existingToDo.getUserId().equals(toDo.getUserId())) {
            throw new InvalidInputException("Invalid input: User ID cannot be changed.");
        }
        toDoDaoJpa.save(toDo);
    }

    @Override
    public Collection<ToDo> findByPriority(Integer priority) {
        return toDoDaoJpa.findByPriority(priority);
    }

    @Override
    public ToDo findById(Long id) {
        return toDoDaoJpa.findById(id).orElseThrow(() -> new EntityNotFoundException("ToDo not found with id: " + id));
    }

    @Override
    public Collection<ToDo> findByUserId(Long userId) {
        if (userJpa.findById(userId).isEmpty()) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        return toDoDaoJpa.findByUserId(userId);
    }

    @Override
    public Collection<ToDo> findByFilters(Integer priority, Date dueDateFrom, Date dueDateTo, Date dueDate) {
        return toDoDaoJpa.findByFilters(priority, dueDateFrom, dueDateTo, dueDate);
    }
}
