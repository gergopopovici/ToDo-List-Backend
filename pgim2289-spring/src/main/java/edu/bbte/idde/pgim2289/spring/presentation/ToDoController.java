package edu.bbte.idde.pgim2289.spring.presentation;

import edu.bbte.idde.pgim2289.spring.dto.RequestToDoDTO;
import edu.bbte.idde.pgim2289.spring.dto.ResponseToDoDTO;
import edu.bbte.idde.pgim2289.spring.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.spring.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.spring.mapper.ToDoMapper;
import edu.bbte.idde.pgim2289.spring.model.ToDo;
import edu.bbte.idde.pgim2289.spring.services.ToDoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/todos")
public class ToDoController {
    private final ToDoService toDoService;
    @Autowired
    private final ToDoMapper toDoMapper;

    @Autowired
    public ToDoController(ToDoService toDoService, ToDoMapper toDoMapper) {
        this.toDoService = toDoService;
        this.toDoMapper = toDoMapper;
    }

    @GetMapping
    public Collection<ResponseToDoDTO> getAllToDos(@RequestParam(required = false) Integer priority) {
        if (priority != null) {
            return toDoService.findByPriority(priority).stream()
                    .map(toDoMapper::toDTO)
                    .toList();
        } else {
            return toDoService.findAll().stream()
                    .map(toDoMapper::toDTO)
                    .toList();
        }
    }

    @GetMapping("/{id}")
    public ResponseToDoDTO getToDoById(@PathVariable Long id) {
        ToDo toDo = toDoService.findById(id);
        return toDoMapper.toDTO(toDo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseToDoDTO createTodo(@Valid @RequestBody RequestToDoDTO
                                              requestToDoDTO) throws InvalidInputException {
        ToDo toDo = toDoMapper.toEntity(requestToDoDTO);
        toDoService.create(toDo);
        return toDoMapper.toDTO(toDo);
    }

    @PutMapping("/{id}")
    public ResponseToDoDTO updateTodo(@PathVariable Long id,
                                      @Valid @RequestBody RequestToDoDTO requestToDoDTO) throws EntityNotFoundException,
            InvalidInputException {
        ToDo updatedToDo = toDoMapper.toEntity(requestToDoDTO);
        updatedToDo.setId(id);
        toDoService.update(updatedToDo);
        return toDoMapper.toDTO(updatedToDo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodoById(@PathVariable Long id, @RequestParam Long userId)
            throws EntityNotFoundException {
        toDoService.delete(id,userId);
    }
    @GetMapping("/user/{userId}")
    public Collection<ResponseToDoDTO> getToDosByUserId(@PathVariable Long userId) throws EntityNotFoundException {
        return toDoService.findByUserId(userId).stream()
                .map(toDoMapper::toDTO)
                .toList();
    }
}
