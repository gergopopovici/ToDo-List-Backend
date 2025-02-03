package edu.bbte.idde.pgim2289.web.servlet.json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.pgim2289.backend.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.backend.model.ToDo;
import edu.bbte.idde.pgim2289.backend.services.ToDoService;
import edu.bbte.idde.pgim2289.backend.services.ToDoServiceImplementation;
import edu.bbte.idde.pgim2289.web.servlet.DTO.RequestToDoDTO;
import edu.bbte.idde.pgim2289.web.servlet.customerrormessages.Error;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;

@WebServlet("/todos/batch")
public class batchServlet extends HttpServlet {
    private final transient ToDoService toDoService = new ToDoServiceImplementation();
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try {
            Collection<ToDo> toDos = objectMapper.readValue(req.getReader(), RequestToDoDTO.class).getTodo();
            toDoService.create2(toDos);
            resp.setStatus(HttpServletResponse.SC_OK);
        }catch (InvalidInputException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            edu.bbte.idde.pgim2289.web.servlet.customerrormessages.Error error = new edu.bbte.idde.pgim2289.web.servlet.customerrormessages.Error(HttpServletResponse.SC_BAD_REQUEST, "Invalid input");
            objectMapper.writeValue(resp.getWriter(), error);
        } catch (JsonMappingException | JsonParseException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            edu.bbte.idde.pgim2289.web.servlet.customerrormessages.Error error = new Error(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON format");
            objectMapper.writeValue(resp.getWriter(), error);
        }
    }
}
