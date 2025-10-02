package com.example.vuln;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FeedbackController {

    private static final Logger logger = LogManager.getLogger(FeedbackController.class);

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletRequest request,
                        Model model) {

        String ua = request.getHeader("User-Agent");
        String clientIp = request.getRemoteAddr();

        // *** ช่องโหว่: log ข้อมูลจากผู้ใช้ตรง ๆ (จะไป trigger JNDI lookup) ***
        logger.info("Login attempt - username='{}', password='{}'", username, password);
        logger.info("User-Agent: {}", ua);
        logger.info("Client IP: {}", clientIp);

        // Simple authentication logic (for demo purposes)
        if ("admin".equals(username) && "password".equals(password)) {
            logger.info("Successful login for user: {}", username);
            model.addAttribute("loginSuccess", true);
            model.addAttribute("username", username);
        } else {
            logger.warn("Failed login attempt for user: {}", username);
            model.addAttribute("loginError", true);
        }

        return "index";
    }

    @PostMapping(path = "/signup", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String signup(@RequestParam("username") String username,
                         @RequestParam("password") String password,
                         @RequestParam("email") String email,
                         HttpServletRequest request,
                         Model model) {

        String ua = request.getHeader("User-Agent");
        String clientIp = request.getRemoteAddr();

        // *** ช่องโหว่: log ข้อมูลจากผู้ใช้ตรง ๆ ***
        logger.info("New user registration - username='{}', email='{}', password='{}'", username, email, password);
        logger.info("User-Agent: {}", ua);
        logger.info("Client IP: {}", clientIp);

        // Simple registration logic
        logger.info("User registration successful for: {}", username);
        model.addAttribute("signupSuccess", true);
        model.addAttribute("username", username);

        return "index";
    }

    // Keep the old feedback endpoint for backward compatibility
    @PostMapping(path = "/feedback", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String feedback(@RequestParam("name") String name,
                           @RequestParam("message") String message,
                           HttpServletRequest request,
                           Model model) {

        String ua = request.getHeader("User-Agent");

        // *** ช่องโหว่: log ข้อมูลจากผู้ใช้ตรง ๆ (จะไป trigger JNDI lookup) ***
        logger.info("New feedback from name='{}', message='{}'", name, message);
        logger.info("User-Agent: {}", ua);

        model.addAttribute("ok", true);
        model.addAttribute("name", name);
        return "index";
    }
}
