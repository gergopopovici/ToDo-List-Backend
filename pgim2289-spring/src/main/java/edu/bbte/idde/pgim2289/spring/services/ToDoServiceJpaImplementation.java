package edu.bbte.idde.pgim2289.spring.services;

import edu.bbte.idde.pgim2289.spring.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.spring.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.spring.model.ToDo;
import edu.bbte.idde.pgim2289.spring.repository.ToDoDaoJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

@Service
@Profile("jpa")
public class ToDoServiceJpaImplementation implements ToDoService {
    private final ToDoDaoJpa toDoDaoJpa;

    @Autowired
    public ToDoServiceJpaImplementation(ToDoDaoJpa toDoDaoJpa) {
        this.toDoDaoJpa = toDoDaoJpa;
    }


    @Override
    public void create(ToDo toDo) throws InvalidInputException {
        validateToDoInput(toDo);
        toDoDaoJpa.save(toDo);
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
        return toDoDaoJpa.findAll();
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        toDoDaoJpa.deleteById(id);
    }

    @Override
    public void update(ToDo toDo) throws EntityNotFoundException, InvalidInputException {
        if (toDo.getTitle() == null || toDo.getTitle().isBlank()
                || toDo.getDescription() == null || toDo.getDescription().isBlank()
                || toDo.getDate() == null) {
            throw new InvalidInputException("Invalid input: title, description, and due date cannot be empty.");
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
}
