package edu.bbte.idde.pgim2289.web.servlet;

import com.github.jknack.handlebars.Template;
import edu.bbte.idde.pgim2289.backend.exceptions.DatabaseException;
import edu.bbte.idde.pgim2289.backend.model.ToDo;
import edu.bbte.idde.pgim2289.backend.repository.DaoFactory;
import edu.bbte.idde.pgim2289.backend.repository.ToDoDao;
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

    private final transient ToDoDao toDoDao = DaoFactory.getInstance().getToDoDao();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException, DatabaseException {

        Map<String, Object> model = new ConcurrentHashMap<>();
        model.put("contextPath", request.getContextPath());

        try {
            Collection<ToDo> todos = toDoDao.findAll();
            model.put("todos", todos);
            Template template = HandleBarsFactory.getTemplate("todoList");
            response.getWriter().println(template.apply(model));

        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("{\"error\":\"Template rendering error\"}");
        } catch (DatabaseException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("{\"error\":\"Unexpected error\"}");
        }
    }
}
