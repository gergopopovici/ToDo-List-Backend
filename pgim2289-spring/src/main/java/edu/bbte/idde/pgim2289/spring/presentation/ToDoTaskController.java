package edu.bbte.idde.pgim2289.spring.presentation;

import edu.bbte.idde.pgim2289.spring.dto.RequestTaskDTO;
import edu.bbte.idde.pgim2289.spring.dto.ResponseTaskDTO;
import edu.bbte.idde.pgim2289.spring.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.spring.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.spring.mapper.TaskMapper;
import edu.bbte.idde.pgim2289.spring.model.Task;
import edu.bbte.idde.pgim2289.spring.model.ToDo;
import edu.bbte.idde.pgim2289.spring.services.ToDoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/todos/{todoId}/tasks")
public class ToDoTaskController {
    private final ToDoService toDoService;
    private final TaskMapper taskMapper;

    @Autowired
    public ToDoTaskController(ToDoService toDoService, TaskMapper taskMapper) {
        this.toDoService = toDoService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public List<ResponseTaskDTO> getTasksByToDoId(@PathVariable Long todoId) throws EntityNotFoundException {
        ToDo toDo = toDoService.findById(todoId);
        return toDo.getTasks().stream()
                .map(taskMapper::toTask)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseTaskDTO addTaskToToDo(@PathVariable Long todoId,
                                         @RequestBody @Valid RequestTaskDTO requestTaskDTO)
            throws InvalidInputException {
        Task task = taskMapper.toEntity(requestTaskDTO);
        ToDo toDo = toDoService.findById(todoId);
        toDo.addTask(task);
        toDoService.update(toDo);
        return taskMapper.toTask(task);
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaskFromToDo(@PathVariable Long todoId,
                                   @PathVariable Long taskId)
            throws EntityNotFoundException {
        ToDo toDo = toDoService.findById(todoId);

        Task task = toDo.getTasks().stream()
                .filter(t -> t.getId().equals(taskId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Task with id "
                        + taskId + " not found for ToDo with id " + todoId));

        toDo.removeTask(task);
        toDoService.update(toDo);
    }
}