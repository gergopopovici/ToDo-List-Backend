package edu.bbte.idde.pgim2289.spring.services;

import edu.bbte.idde.pgim2289.spring.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.spring.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.spring.model.ToDo;
import edu.bbte.idde.pgim2289.spring.repository.repo.ToDoJpaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

@Service
@Profile("jpa")
public class ToDoServiceJpaImplementation implements ToDoService {
    private final ToDoJpaRepo toDoJpaRepo;

    @Autowired
    public ToDoServiceJpaImplementation(ToDoJpaRepo toDoJpaRepo) {
        this.toDoJpaRepo = toDoJpaRepo;
    }


    @Override
    public void create(ToDo toDo) throws InvalidInputException {
        validateToDoInput(toDo);
        toDoJpaRepo.save(toDo);
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
        return toDoJpaRepo.findAll().stream().filter(toDo -> !toDo.getDeleted()).toList();
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        ToDo toDo = findById(id);
        toDo.setDeleted(true);
        update(toDo);
    }

    @Override
    public void update(ToDo toDo) throws EntityNotFoundException, InvalidInputException {
        if (toDo.getTitle() == null || toDo.getTitle().isBlank()
                || toDo.getDescription() == null || toDo.getDescription().isBlank()
                || toDo.getDate() == null) {
            throw new InvalidInputException("Invalid input: title, description, and due date cannot be empty.");
        }
        toDoJpaRepo.save(toDo);
    }

    @Override
    public Collection<ToDo> findByPriority(Integer priority) {
        return toDoJpaRepo.findByPriority(priority);
    }

    @Override
    public ToDo findById(Long id) {
        ToDo toDo = toDoJpaRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("ToDo not found with id: " + id));
        if (toDo.getDeleted()) {
            throw new EntityNotFoundException("ToDo not found with id: " + id);
        }
        return toDo;
    }
}
