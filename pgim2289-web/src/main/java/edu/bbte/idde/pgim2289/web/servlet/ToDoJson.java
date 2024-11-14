package edu.bbte.idde.pgim2289.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.pgim2289.backend.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.backend.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.backend.model.ToDo;
import edu.bbte.idde.pgim2289.backend.repository.DaoFactory;
import edu.bbte.idde.pgim2289.backend.repository.ToDoDao;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;

@WebServlet("/todos")
public class ToDoJson extends HttpServlet {
    private final transient ToDoDao toDoDao = DaoFactory.getInstance().getToDoDao();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        response.setContentType("application/json");

        if (idParam != null) {
            try {
                Long id = Long.parseLong(idParam);
                ToDo todo = toDoDao.findById(id);
                if (todo != null) {
                    response.getWriter().println(objectMapper.writeValueAsString(todo));
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("{\"error\":\"Invalid ID format\"}");
            } catch (EntityNotFoundException e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println("{\"error\":\"Entity with ID "
                        + request.getParameter("id") + " not found\"}");
            }
        } else {
            Collection<ToDo> todos = toDoDao.findAll();
            response.getWriter().println(objectMapper.writeValueAsString(todos));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (BufferedReader reader = request.getReader()) {
            ToDo todo = objectMapper.readValue(reader, ToDo.class);
            toDoDao.create(todo);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().println(objectMapper.writeValueAsString(todo));
        } catch (InvalidInputException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("{\"error\":\"Invalid input\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("{\"error\":\"ID parameter is required\"}");
            return;
        }
        try {
            Long id = Long.parseLong(idParam);
            ToDo todo = toDoDao.findById(id);
            if (todo != null) {
                toDoDao.delete(id);
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("{\"error\":\"Invalid ID format\"}");
        } catch (EntityNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().println("{\"error\":\"Entity with ID "
                    + request.getParameter("id") + " not found\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("{\"error\":\"ID parameter is required\"}");
            return;
        }

        try {
            Long id = Long.parseLong(idParam);
            ToDo oldToDo = toDoDao.findById(id);
            if (oldToDo != null) {
                try (BufferedReader reader = request.getReader()) {
                    ToDo updatedToDo = objectMapper.readValue(reader, ToDo.class);
                    updatedToDo.setId(id);
                    toDoDao.update(updatedToDo);
                    response.getWriter().println(objectMapper.writeValueAsString(updatedToDo));
                } catch (IOException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().println("{\"error\":\"Invalid input\"}");
                }
            }
        } catch (EntityNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().println("{\"error\":\"Entity with ID "
                    + request.getParameter("id") + " not found\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("{\"error\":\"Invalid ID format\"}");
        } catch (InvalidInputException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("{\"error\":\"Invalid input\"}");
        }
    }
}
