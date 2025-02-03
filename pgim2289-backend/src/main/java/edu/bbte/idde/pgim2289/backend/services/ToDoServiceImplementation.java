package edu.bbte.idde.pgim2289.backend.services;

import edu.bbte.idde.pgim2289.backend.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.backend.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.backend.model.ToDo;
import edu.bbte.idde.pgim2289.backend.repository.DaoFactory;
import edu.bbte.idde.pgim2289.backend.repository.ToDoDao;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;

@Slf4j
public class ToDoServiceImplementation implements ToDoService {
    private final ToDoDao toDoDao;

    public ToDoServiceImplementation() {
        toDoDao = DaoFactory.getInstance().getToDoDao();
    }


    @Override
    public void create(ToDo toDo) throws InvalidInputException {
        validateToDoInput(toDo);
        toDo.setCreationDate(Instant.now());
        toDoDao.create(toDo);
        log.info("todo creation date {}",toDo.getCreationDate());
        log.info("todo{}",toDo);
    }

    private void validateToDoInput(ToDo toDo) throws InvalidInputException {
        validateTitle(toDo.getTitle());
        validateDescription(toDo.getDescription());
        validateDate(toDo.getDate());
        validatePriority(toDo.getPriority());
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
        return toDoDao.findAll();
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        toDoDao.delete(id);
    }

    @Override
    public void update(ToDo toDo) throws EntityNotFoundException, InvalidInputException {
        if (toDo.getTitle() == null || toDo.getTitle().isBlank()
                || toDo.getDescription() == null || toDo.getDescription().isBlank()
                || toDo.getDate() == null) {
            throw new InvalidInputException("Invalid input: title, description, and due date cannot be empty.");
        }
        toDoDao.update(toDo);
    }

    @Override
    public Collection<ToDo> findByPriority(Integer priority) {
        return toDoDao.findByPriority(priority);
    }

    @Override
    public ToDo findById(Long id) {
        ToDo toDo = toDoDao.findById(id);
        log.info("todo find by ID {}", toDo);
        return toDo;
    }

}
