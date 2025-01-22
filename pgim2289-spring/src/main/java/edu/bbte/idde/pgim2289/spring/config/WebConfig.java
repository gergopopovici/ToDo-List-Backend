package edu.bbte.idde.pgim2289.spring.config;

import edu.bbte.idde.pgim2289.spring.interceptor.LoggingInterceptor;
import edu.bbte.idde.pgim2289.spring.interceptor.MiddlewareInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LoggingInterceptor loggingInterceptor;
    private final MiddlewareInterceptor middlewareInterceptor;

    public WebConfig(LoggingInterceptor loggingInterceptor, MiddlewareInterceptor middlewareInterceptor) {
        this.loggingInterceptor = loggingInterceptor;
        this.middlewareInterceptor = middlewareInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor).addPathPatterns("/**");
        registry.addInterceptor(middlewareInterceptor).addPathPatterns("/api/todos/*/tasks");
    }
}
