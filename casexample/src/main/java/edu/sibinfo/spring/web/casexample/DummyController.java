package edu.sibinfo.spring.web.casexample;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import java.security.Principal;

@Controller
public class DummyController {

    private static void providePrincipal(HttpServletRequest request, Model model) {
        Principal principal = request.getUserPrincipal();
        model.addAttribute("principal", principal);
    }

    @RequestMapping("/")
    public String index(HttpServletRequest request, Model model) {
        providePrincipal(request, model);
        return "index";
    }

    @RequestMapping("/secured")
    public String secured(HttpServletRequest request, Model model) {
        providePrincipal(request, model);
        return "secured";
    }

    @RequestMapping("/admin")
    public String admin(HttpServletRequest request, Model model) {
        providePrincipal(request, model);
        return "admin";
    }
}
