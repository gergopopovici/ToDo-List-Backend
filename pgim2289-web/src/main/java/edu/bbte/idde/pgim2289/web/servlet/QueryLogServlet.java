package edu.bbte.idde.pgim2289.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.pgim2289.backend.services.ToDoService;
import edu.bbte.idde.pgim2289.backend.services.ToDoServiceImplementation;
import edu.bbte.idde.pgim2289.web.servlet.DTO.StatDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/query-log")
public class QueryLogServlet extends HttpServlet {
    ToDoService toDoService = new ToDoServiceImplementation();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StatDTO statDTO = new StatDTO();
        statDTO.setLogQueries(toDoService.getLogQueryCount());
        statDTO.setLogUpdates(toDoService.getLogUpdateCount());
        if(statDTO.getLogQueries() == null && statDTO.getLogUpdates() == null){
            resp.setStatus(404);
        } else {
            resp.setStatus(200);
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getWriter(), statDTO);
        }
    }
}
