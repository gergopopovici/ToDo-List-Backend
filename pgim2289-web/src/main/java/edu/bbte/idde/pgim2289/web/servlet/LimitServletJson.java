package edu.bbte.idde.pgim2289.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.pgim2289.backend.repository.ToDoServiceImplFactory;
import edu.bbte.idde.pgim2289.backend.services.ToDoServiceImplementation;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet("/limit")
public class LimitServletJson extends HttpServlet {
    private final transient ToDoServiceImplementation toDoService = ToDoServiceImplFactory.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Long limit = toDoService.getLimit();
        String response = "The limit is: " + limit;
        objectMapper.writeValue(resp.getWriter(), response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Long limit = objectMapper.readValue(req.getReader(),PostLimitRequest.class).getLimit();
        toDoService.setLimit(limit);
        String response = "The limit is set to: " + limit;
        objectMapper.writeValue(resp.getWriter(), response);
    }
}
