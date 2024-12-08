package edu.bbte.idde.pgim2289.spring.presentation;

import edu.bbte.idde.pgim2289.spring.dto.ToDoDTO;
import edu.bbte.idde.pgim2289.spring.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.spring.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.spring.mapper.ToDoMapper;
import edu.bbte.idde.pgim2289.spring.model.ToDo;
import edu.bbte.idde.pgim2289.spring.services.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/todos")
public class ToDoController {
    private final ToDoService toDoService;
    private final ToDoMapper toDoMapper;

    @Autowired
    public ToDoController(ToDoService toDoService, ToDoMapper toDoMapper) {
        this.toDoService = toDoService;
        this.toDoMapper = toDoMapper;
    }
    @GetMapping
    public Collection<ToDoDTO>getAllToDos(){
        return toDoService.findAll().stream()
                .map(toDoMapper::toDTO)
                .toList();
    }
    @GetMapping("/{id}")
    public ToDoDTO getToDoById(@PathVariable Long id){
        return toDoMapper.toDTO(toDoService.findById(id));
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTodo(@RequestBody ToDoDTO toDoDTO) throws InvalidInputException {
        ToDo toDo = toDoMapper.toEntity(toDoDTO);
        toDoService.create(toDo);
    }
    @PutMapping("/{id}")
    public void updateTodo(@PathVariable Long id, @RequestBody ToDoDTO toDoDTO) throws EntityNotFoundException, InvalidInputException {
        ToDo updatedToDo = toDoMapper.toEntity(toDoDTO);
        updatedToDo.setId(id);
        toDoService.update(updatedToDo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodoById(@PathVariable Long id) throws EntityNotFoundException {
        toDoService.delete(id);
    }

    @GetMapping("/search")
    public Collection<ToDoDTO> getTodosByPriority(@RequestParam Integer priority) {
        return toDoService.findByPriority(priority).stream()
                .map(toDoMapper::toDTO)
                .toList();
    }
}
