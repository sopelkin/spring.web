package edu.sibinfo.spring.web.oauth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class DemoController {

    @RequestMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }
}
