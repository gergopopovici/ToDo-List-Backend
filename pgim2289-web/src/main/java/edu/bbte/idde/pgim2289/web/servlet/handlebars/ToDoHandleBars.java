package edu.bbte.idde.pgim2289.web.servlet.handlebars;

import com.github.jknack.handlebars.Template;
import edu.bbte.idde.pgim2289.backend.exceptions.DatabaseException;
import edu.bbte.idde.pgim2289.backend.model.ToDo;
import edu.bbte.idde.pgim2289.backend.services.ToDoService;
import edu.bbte.idde.pgim2289.backend.services.ToDoServiceImplementation;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/webserver/handlebars")
public class ToDoHandleBars extends HttpServlet {

    private final transient ToDoService toDoService = new ToDoServiceImplementation();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException, DatabaseException {

        Map<String, Object> model = new ConcurrentHashMap<>();
        model.put("contextPath", request.getContextPath());

        try {
            Collection<ToDo> todos = toDoService.findAll();
            model.put("todos", todos);
            Template template = HandleBarsFactory.getTemplate("todoList");
            response.getWriter().println(template.apply(model));

        } catch (IOException | DatabaseException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            model.put("errorMessage", "An unexpected error occurred while processing your request{}.");
            Template errorTemplate = HandleBarsFactory.getTemplate("error");
            response.getWriter().println(errorTemplate.apply(model));
        }
    }
}
