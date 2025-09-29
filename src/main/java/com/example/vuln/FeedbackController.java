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
