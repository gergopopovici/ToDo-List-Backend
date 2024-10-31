package edu.bbte.idde.pgim2289.services;

import edu.bbte.idde.pgim2289.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.model.ToDo;
import edu.bbte.idde.pgim2289.repository.DaoFactory;
import edu.bbte.idde.pgim2289.repository.ToDoDao;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

public class ToDoServiceImplementation implements ToDoService {
    private final ToDoDao toDoDao;

    public ToDoServiceImplementation() {
        toDoDao = DaoFactory.getInstance().getToDoDao();
    }

    @Override
    public void create(ToDo toDo) throws InvalidInputException {
        if (toDo.getTitle() == null || toDo.getTitle().isBlank() ||
            toDo.getDescription() == null || toDo.getDescription().isBlank() ||
            toDo.getDate() == null) {
            throw new InvalidInputException("Invalid input: title, description, and due date cannot be empty.");
        }
        toDoDao.create(toDo);
    }
    @Override
    public Collection<ToDo> findAll() {
        return toDoDao.findAll();
    }

    @Override
    public void delete(Long ID) throws EntityNotFoundException {
        toDoDao.delete(ID);
    }
    @Override
    public void update(ToDo toDo) throws EntityNotFoundException, InvalidInputException{
        if (toDo.getTitle() == null || toDo.getTitle().isBlank() ||
            toDo.getDescription() == null || toDo.getDescription().isBlank() ||
            toDo.getDate() == null) {
            throw new InvalidInputException("Invalid input: title, description, and due date cannot be empty.");
        }
        toDoDao.update(toDo);
    }
    @Override
    public Collection<ToDo> findByPriority(Integer Priority) {
        return toDoDao.findByPriority(Priority);
    }

}
