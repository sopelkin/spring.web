package edu.sibinfo.spring.web.module01;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GreetingController {
    @RequestMapping("/greeting")
    public String greeting(Model model) {
    	model.addAttribute("timeStamp", LocalDateTime.now().toString());
        return "greeting";
    }
}
