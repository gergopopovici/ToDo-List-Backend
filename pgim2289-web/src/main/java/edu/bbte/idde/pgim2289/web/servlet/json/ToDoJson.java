package edu.bbte.idde.pgim2289.web.servlet.json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.pgim2289.backend.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.backend.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.backend.model.ToDo;
import edu.bbte.idde.pgim2289.backend.services.ToDoService;
import edu.bbte.idde.pgim2289.backend.services.ToDoServiceImplementation;
import edu.bbte.idde.pgim2289.web.servlet.customerrormessages.Error;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;

@Slf4j
@WebServlet("/todos")
public class ToDoJson extends HttpServlet {
    private final transient ToDoService toDoService = new ToDoServiceImplementation();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");

        response.setContentType("application/json");

        if (idParam != null) {
            try {
                Long id = Long.parseLong(idParam);
                ToDo todo = toDoService.findById(id);
                if (todo != null) {
                    objectMapper.writeValue(response.getWriter(), todo);
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                Error error = new Error(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
                objectMapper.writeValue(response.getWriter(), error);
            } catch (EntityNotFoundException e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                Error error = new Error(HttpServletResponse.SC_NOT_FOUND, "Entity with ID "
                        + request.getParameter("id") + " not found");
                objectMapper.writeValue(response.getWriter(), error);
            }
        } else {
            Collection<ToDo> todos = toDoService.findAll();
            objectMapper.writeValue(response.getWriter(), todos);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        try (BufferedReader reader = request.getReader()) {
            ToDo todo = objectMapper.readValue(reader, ToDo.class);
            if(todo.getCreationDate()!=null){
                throw new InvalidInputException("Creation date can't be set by the user");
            }
            toDoService.create(todo);
            // log.info("todo {}",todo);
            response.setStatus(HttpServletResponse.SC_CREATED);
            objectMapper.writeValue(response.getWriter(), todo);
        } catch (InvalidInputException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Error error = new Error(HttpServletResponse.SC_BAD_REQUEST, "Invalid input");
            objectMapper.writeValue(response.getWriter(), error);
        } catch (JsonMappingException | JsonParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Error error = new Error(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON format");
            objectMapper.writeValue(response.getWriter(), error);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Error error = new Error(HttpServletResponse.SC_BAD_REQUEST, "ID parameter is required");
            objectMapper.writeValue(response.getWriter(), error);
            return;
        }
        try {
            Long id = Long.parseLong(idParam);
            ToDo todo = toDoService.findById(id);
            if (todo != null) {
                toDoService.delete(id);
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Error error = new Error(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
            objectMapper.writeValue(response.getWriter(), error);
        } catch (EntityNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            Error error = new Error(HttpServletResponse.SC_NOT_FOUND, "Entity with ID "
                    + request.getParameter("id") + " not found");
            objectMapper.writeValue(response.getWriter(), error);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Error error = new Error(HttpServletResponse.SC_BAD_REQUEST, "ID parameter is required");
            objectMapper.writeValue(response.getWriter(), error);
            return;
        }

        try {
            Long id = Long.parseLong(idParam);
            ToDo oldToDo = toDoService.findById(id);
            if (oldToDo != null) {
                try (BufferedReader reader = request.getReader()) {
                    ToDo updatedToDo = objectMapper.readValue(reader, ToDo.class);
                    updatedToDo.setId(id);
                    toDoService.update(updatedToDo);
                    objectMapper.writeValue(response.getWriter(), updatedToDo);
                } catch (JsonMappingException | JsonParseException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    Error error = new Error(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON format");
                    objectMapper.writeValue(response.getWriter(), error);
                } catch (IOException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    Error error = new Error(HttpServletResponse.SC_BAD_REQUEST, "Invalid input");
                    objectMapper.writeValue(response.getWriter(), error);
                }
            }
        } catch (EntityNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            Error error = new Error(HttpServletResponse.SC_NOT_FOUND, "Entity with ID "
                    + request.getParameter("id") + " not found");
            objectMapper.writeValue(response.getWriter(), error);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Error error = new Error(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
            objectMapper.writeValue(response.getWriter(), error);
        } catch (InvalidInputException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Error error = new Error(HttpServletResponse.SC_BAD_REQUEST, "Invalid input");
            objectMapper.writeValue(response.getWriter(), error);
        }
    }
}
