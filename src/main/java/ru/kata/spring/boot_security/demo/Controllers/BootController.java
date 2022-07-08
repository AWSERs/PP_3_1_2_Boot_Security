package ru.kata.spring.boot_security.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BootController {

    @GetMapping("/boot")
    public String getBoot(){
        return "admin_panel";
    }
}
