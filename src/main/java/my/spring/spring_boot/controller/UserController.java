package my.spring.spring_boot.controller;

import lombok.AllArgsConstructor;
import my.spring.spring_boot.model.User;
import my.spring.spring_boot.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@AllArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public String userPage(ModelMap model, Authentication auth) {
        User user = userService.getUserByUsername(auth.getName());
        model.addAttribute("user", user);
        return "index";
    }
}
