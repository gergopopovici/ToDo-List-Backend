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
    private static Long nextId = 1L;
    private final ToDoDao toDoDao;

    public ToDoServiceImplementation() {
        toDoDao = DaoFactory.getInstance().getToDoDao();
    }

    @Override
    public void create(String title, String description, String dueDate, String priority) throws ParseException {
        if(Objects.equals(title, "") || title.trim().isEmpty() ||
           Objects.equals(description, "") || description.trim().isEmpty() ||
           Objects.equals(dueDate, "") || dueDate.trim().isEmpty()){
            throw new InvalidInputException("Invalid input");
        }
        Date dueDateNew = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dueDateNew = dateFormat.parse(dueDate);
        Integer integer;
        integer = Integer.parseInt(priority);
        ToDo toDo = new ToDo(nextId++, title, description, dueDateNew, integer);
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
    public void update(Long iD,String title, String description, String dueDate, String priority) throws EntityNotFoundException, ParseException{
        if(Objects.equals(title, "") || title.trim().isEmpty() ||
           Objects.equals(description, "") || description.trim().isEmpty() ||
           Objects.equals(dueDate, "") || dueDate.trim().isEmpty()){
            throw new InvalidInputException("Invalid input");
        }
        Date dueDateNew = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dueDateNew = dateFormat.parse(dueDate);
        Integer integer= Integer.parseInt(priority);
        ToDo toDo = new ToDo(iD, title, description, dueDateNew, integer);
        toDoDao.update(toDo);
    }
    @Override
    public Collection<ToDo> findByPriority(Integer Priority) {
        return toDoDao.findByPriority(Priority);
    }

}
