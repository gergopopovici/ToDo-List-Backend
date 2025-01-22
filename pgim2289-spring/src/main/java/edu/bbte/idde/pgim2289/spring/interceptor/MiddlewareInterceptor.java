package edu.bbte.idde.pgim2289.spring.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.time.LocalTime;

@Component
public class MiddlewareInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
            throws IOException {
        LocalTime now = LocalTime.now();
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(19, 0);

        if (now.isBefore(start) || now.isAfter(end)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Requests are only allowed between 9 AM and 5 PM.");
            return false;
        }

        return true;
    }
}
