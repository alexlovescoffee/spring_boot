package my.spring.spring_boot.controller;

import lombok.AllArgsConstructor;
import my.spring.spring_boot.dto.UserDto;
import my.spring.spring_boot.service.UserService;
import my.spring.spring_boot.service.UserDtoService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("admin")
@Validated
public class AdminController {

    private final UserService userService;
    private final UserDtoService userDtoService;

    @GetMapping
    public String bootPage(ModelMap model, Authentication auth) {
        List<UserDto> users = userService.getAllUsers()
                .stream()
                .map(userDtoService::from_User_ToUserDto)
                .collect(Collectors.toList());
        model.addAttribute("users", users);
        model.addAttribute("current", users
                .stream()
                .filter(u -> u.getEmail().equals(auth.getName()))
                .findFirst()
                .orElseThrow()
        );
        return "boot";
    }
}
