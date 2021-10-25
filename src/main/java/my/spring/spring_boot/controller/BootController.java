package my.spring.spring_boot.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import my.spring.spring_boot.dto.Test;
import my.spring.spring_boot.dto.UserDto;
import my.spring.spring_boot.dto.jsonview.OnAdd;
import my.spring.spring_boot.dto.jsonview.OnEdit;
import my.spring.spring_boot.dto.jsonview.OnGet;
import my.spring.spring_boot.dto.jsonview.OnTwo;
import my.spring.spring_boot.exception.servicelevel.DataNotSavedException;
import my.spring.spring_boot.model.User;
import my.spring.spring_boot.service.UserService;
import my.spring.spring_boot.util.WebUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("boot")
@Validated
public class BootController {

    private final UserService userService;
    private final WebUtil webUtil;

    @GetMapping
    public String bootPage(ModelMap model) {
        List<UserDto> users = userService.getAllUsers()
                .stream()
                .map(webUtil::from_User_ToUserDto)
                .collect(Collectors.toList());
        model.addAttribute("users", users);
        return "boot";
    }

    @GetMapping("get-user")
    @JsonView(OnGet.class)
    @ResponseBody
    public ResponseEntity<UserDto> getUser(@RequestParam("id") long id) {
        UserDto userDto = webUtil.from_User_ToUserDto(userService.getUserById(id));

        return ResponseEntity.ok(userDto);
    }

    @PostMapping
    @ResponseBody
    @JsonView(OnGet.class)
    @Validated(value = OnAdd.class)
    public ResponseEntity<?> addUser(@Valid @RequestBody @JsonView(OnAdd.class) UserDto userDto) {
        System.out.println(userDto);
        try {
            User newUser = webUtil.from_UserDto_ToUser(userDto);
            userService.addUser(newUser);
            return ResponseEntity.ok(webUtil.from_User_ToUserDto(newUser));
        } catch (DataNotSavedException e) {
            return ResponseEntity.badRequest().body("Error! User not added! Message: " + e.getMessage());
        }
    }

    @PutMapping
    @ResponseBody
    @Validated(OnEdit.class)
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody @JsonView(OnEdit.class) UserDto userDto) {
        System.out.println(userDto);

        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping
    public void deleteUser(@RequestParam("id") long id) {
        System.out.println(id);
    }









    @GetMapping("test")
    @ResponseBody
    @JsonView(OnGet.class)
    public ResponseEntity<Test> getTest() {
        Test test = new Test(100, "Alex", "Supa");
        return ResponseEntity.ok(test);
    }

    @PostMapping("test2")
    @ResponseBody
    @JsonView(OnGet.class)
    public ResponseEntity<Test> getTest2(@RequestBody @JsonView(OnTwo.class) Test test) {
        System.out.println("Request: ");
        System.out.println(test);

        Test response = new Test(100, "Alex", "Supa");
        System.out.println("Response: ");
        System.out.println(response);

        return ResponseEntity.ok(response);
    }

    @PostMapping("test3")
    @ResponseBody
    @JsonView(OnGet.class)
    public ResponseEntity<UserDto> getTest3(@RequestBody /*@JsonView()*/ UserDto userDto) {
        System.out.println("Request: ");
        System.out.println(userDto);

        User user = webUtil.from_UserDto_ToUser(userDto);
        System.out.println("User mapped: ");
        System.out.println(user);

        return ResponseEntity.ok(userDto);
    }
}
