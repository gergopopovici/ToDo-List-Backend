package edu.bbte.idde.pgim2289.spring.presentation;

import edu.bbte.idde.pgim2289.spring.dto.RequestTaskDTO;
import edu.bbte.idde.pgim2289.spring.dto.ResponseTaskDTO;
import edu.bbte.idde.pgim2289.spring.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.spring.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.spring.mapper.TaskMapper;
import edu.bbte.idde.pgim2289.spring.model.Task;
import edu.bbte.idde.pgim2289.spring.model.ToDo;
import edu.bbte.idde.pgim2289.spring.repository.repo.TaskJpaRepo;
import edu.bbte.idde.pgim2289.spring.services.ToDoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Profile("jpa")
@RestController
@RequestMapping("/api/todos/{todoId}/tasks")
public class ToDoJpaTaskController {
    private final ToDoService toDoService;
    private final TaskMapper taskMapper;
    private final TaskJpaRepo taskJpaRepo;

    @Autowired
    public ToDoJpaTaskController(ToDoService toDoService, TaskMapper taskMapper, TaskJpaRepo taskJpaRepo) {
        this.toDoService = toDoService;
        this.taskMapper = taskMapper;
        this.taskJpaRepo = taskJpaRepo;
    }

    @GetMapping
    public Map<String, Object> getTasksByToDoId(@PathVariable Long todoId,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(defaultValue = "id") String sort,
                                                @RequestParam(defaultValue = "asc") String direction)
                                                throws EntityNotFoundException {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
        Page<Task> taskPage = taskJpaRepo.findByToDoId(todoId, pageable);
        Map<String, Object> response = new ConcurrentHashMap<>();
        response.put("data", taskPage.getContent().stream().map(taskMapper::toTask).collect(Collectors.toList()));
        response.put("page", taskPage.getNumber());
        response.put("size", taskPage.getSize());
        response.put("totalElements", taskPage.getTotalElements());
        response.put("totalPages", taskPage.getTotalPages());
        return response;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseTaskDTO addTaskToToDo(@PathVariable Long todoId,
                                         @RequestBody @Valid RequestTaskDTO requestTaskDTO)
            throws InvalidInputException {
        Task task = taskMapper.toEntity(requestTaskDTO);
        ToDo toDo = toDoService.findById(todoId);
        task.setToDo(toDo);
        taskJpaRepo.save(task);
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