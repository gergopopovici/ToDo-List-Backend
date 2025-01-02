package edu.bbte.idde.pgim2289.spring.presentation;

import edu.bbte.idde.pgim2289.spring.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.spring.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.spring.model.Task;
import edu.bbte.idde.pgim2289.spring.model.ToDo;
import edu.bbte.idde.pgim2289.spring.services.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos/{todoId}/tasks")
public class ToDoTaskController {
    private final ToDoService toDoService;

    @Autowired
    public ToDoTaskController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping
    public List<Task> getTasksByToDoId(@PathVariable Long todoId) throws EntityNotFoundException {
        ToDo toDo = toDoService.findById(todoId);
        return toDo.getTasks();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task addTaskToToDo(@PathVariable Long todoId, @RequestBody Task task) throws InvalidInputException {
        if (task.getDescription() == null || task.getDescription().isBlank()) {
            throw new InvalidInputException("Task description cannot be empty or null.");
        }
        ToDo toDo = toDoService.findById(todoId);
        toDo.addTask(task);
        toDoService.update(toDo);
        return task;
    }


    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaskFromToDo(@PathVariable Long todoId, @PathVariable Long taskId)
            throws EntityNotFoundException {
        ToDo toDo = toDoService.findById(todoId);

        Task task = toDo.getTasks().stream()
                .filter(t -> t.getId().equals(taskId))
                .findFirst()
                .orElseThrow(() -> new
                        EntityNotFoundException("Task with id " + taskId
                        + " not found for ToDo with id " + todoId));

        toDo.removeTask(task);
        toDoService.update(toDo);
    }

}
