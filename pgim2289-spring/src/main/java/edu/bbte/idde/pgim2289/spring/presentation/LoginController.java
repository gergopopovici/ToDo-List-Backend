package edu.bbte.idde.pgim2289.spring.presentation;

import edu.bbte.idde.pgim2289.spring.dto.LoginRequest;
import edu.bbte.idde.pgim2289.spring.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.spring.model.User;
import edu.bbte.idde.pgim2289.spring.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public User login(@RequestBody @Valid LoginRequest loginRequest, HttpServletRequest request) {
        Logger logger = LoggerFactory.getLogger(LoginController.class);
        User user = userService.findByUsername(loginRequest.getUsername());

        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), null, new ArrayList<>());

            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);

            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);

            logger.info("Login successful. JSESSIONID created.");
            return user;
        } else {
            throw new InvalidInputException("Invalid username or password");
        }
    }
}