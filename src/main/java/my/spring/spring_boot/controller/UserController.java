package my.spring.spring_boot.controller;

import lombok.AllArgsConstructor;
import my.spring.spring_boot.dto.UserDto;
import my.spring.spring_boot.service.UserService;
import my.spring.spring_boot.service.UserDtoService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    private final UserDtoService userDtoService;

    @GetMapping
    public String userPage(ModelMap model, Authentication auth) {
        UserDto userDto = userDtoService.from_User_ToUserDto(userService.getUserByEmail(auth.getName()));
        model.addAttribute("current", userDto);
        return "boot";
    }
}