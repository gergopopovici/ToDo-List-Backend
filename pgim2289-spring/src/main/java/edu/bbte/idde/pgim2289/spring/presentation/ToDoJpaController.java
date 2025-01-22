package edu.bbte.idde.pgim2289.spring.presentation;

import edu.bbte.idde.pgim2289.spring.dto.RequestToDoDTO;
import edu.bbte.idde.pgim2289.spring.dto.ResponseToDoDTO;
import edu.bbte.idde.pgim2289.spring.dto.ToDoFilterDTO;
import edu.bbte.idde.pgim2289.spring.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.spring.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.spring.mapper.ToDoMapper;
import edu.bbte.idde.pgim2289.spring.model.ToDo;
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
@RequestMapping("/api/todos")
public class ToDoJpaController {

    private final ToDoService toDoService;
    @Autowired
    private final ToDoMapper toDoMapper;

    @Autowired
    public ToDoJpaController(ToDoService toDoService, ToDoMapper toDoMapper) {
        this.toDoService = toDoService;
        this.toDoMapper = toDoMapper;
    }

    @GetMapping
    public Map<String, Object> getAllToDos(@ModelAttribute ToDoFilterDTO filterDTO) {

        Pageable pageable = PageRequest.of(filterDTO.getPage(), filterDTO.getSize(),
                Sort.by(Sort.Direction.fromString(filterDTO.getDirection()),
                        filterDTO.getSort()));
        Page<ToDo> toDoPage;
        if (filterDTO.getPriority() != null || filterDTO.getTitle() != null
                || filterDTO.getDescription() != null
                || filterDTO.getDueDate() != null) {
            toDoPage = toDoService.filterToDos(filterDTO, pageable);
        } else {
            toDoPage = toDoService.findAll(pageable);
        }

        Map<String, Object> response = new ConcurrentHashMap<>();
        response.put("data", toDoPage.getContent().stream()
                .map(toDoMapper::toDTO)
                .collect(Collectors.toList()));
        response.put("page", toDoPage.getNumber());
        response.put("size", toDoPage.getSize());
        response.put("totalPages", toDoPage.getTotalPages());
        return response;
    }

    @GetMapping("/{id}")
    public ResponseToDoDTO getToDoById(@PathVariable Long id) {
        ToDo toDo = toDoService.findById(id);
        return toDoMapper.toDTO(toDo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseToDoDTO createTodo(@Valid @RequestBody RequestToDoDTO requestToDoDTO) throws InvalidInputException {
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
    public void deleteTodoById(@PathVariable Long id) throws EntityNotFoundException {
        toDoService.delete(id);
    }
}
