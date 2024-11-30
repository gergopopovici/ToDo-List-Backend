package edu.bbte.idde.pgim2289.web.servlet;

import com.github.jknack.handlebars.Template;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/webserver/login")
public class LoginServlet extends HttpServlet {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("GET request to /webserver/login");
        Map<String, Object> model = new ConcurrentHashMap<>();
        model.put("contextPath", request.getContextPath());
        Template template = HandleBarsFactory.getTemplate("login");
        response.getWriter().println(template.apply(model));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        logger.info("username: " + username + " password: " + password);

        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            logger.info("User logged in: " + username);
            response.sendRedirect(request.getContextPath() + "/webserver/handlebars");
        } else {
            Map<String, Object> model = new ConcurrentHashMap<>();
            model.put("errorMessage", "Invalid username or password");
            model.put("contextPath", request.getContextPath());
            Template template = HandleBarsFactory.getTemplate("login");
            response.getWriter().println(template.apply(model));
        }
    }
}