package my.spring.spring_boot.controller;

import lombok.*;
import my.spring.spring_boot.exception.servicelevel.DataNotSavedException;
import my.spring.spring_boot.model.User;
import my.spring.spring_boot.service.UserService;
import my.spring.spring_boot.util.WebUtil;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("admin")
public class AdminController {

    private final UserService userService;
    private final WebUtil webUtil;

    @GetMapping
    public String adminPage(ModelMap model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "index";
    }

    @ResponseBody
    @GetMapping(path = "get", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    public String getUsers() {
        return webUtil.getListOfUsersLikeJson(userService.getAllUsers());
    }

    @ResponseBody
    @PostMapping(path = "add", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    public ResponseEntity<String> addUser(@RequestBody final User user) {
        try {
            userService.addUser(user);
            return ResponseEntity.ok(getUsers());
        } catch (DataNotSavedException e) {
            return ResponseEntity.badRequest().body("Error! User not added! Message: " + e.getMessage());
        }
    }

    @ResponseBody
    @PostMapping(path = "update", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    public ResponseEntity<String> updateUser(@RequestBody final User user, Authentication auth) {
        try {
            long currentUserId = userService.getUserByUsername(auth.getName()).getId();
            userService.updateUser(user);

            //если пользователь изменил себя, то произойдет logout
            if (currentUserId == user.getId()) {
                System.out.println("auth = false");
                auth.setAuthenticated(false);
                return ResponseEntity.status(205).header("re-authenticate", "/logout").build();
            }

            return ResponseEntity.ok(getUsers());
        } catch (DataNotSavedException e) {
            return ResponseEntity.badRequest().body("Error! User not updated! Message: " + e.getMessage());
        }
    }

    @ResponseBody
    @PostMapping(path = "delete", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    public ResponseEntity<String> deleteUser(@RequestParam(name = "id") long id, Authentication auth) {
        try {
            long currentUserId = userService.getUserByUsername(auth.getName()).getId();
            userService.deleteUserById(id);

            //если пользователь удалил себя, то произойдет logout
            if (currentUserId == id)
                return ResponseEntity.status(205).header("re-authenticate", "/logout").build();

            return ResponseEntity.ok(getUsers());
        } catch (DataNotSavedException e) {
            return ResponseEntity.badRequest().body("Error! User not deleted! Message: " + e.getMessage());
        }
    }
}

//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Getter @Setter
//    @ToString
//    static class Test {
//        private int age;
//        private String name;
//        //private Set<Role> roles;
//        private Set<Cat> cats;
//
//        @NoArgsConstructor
//        @AllArgsConstructor
//        @Getter @Setter @ToString
//        private static class Cat {
//            private int id;
//            private String name;
//        }
//    }
//
//    @ResponseBody
//    @PostMapping(path = "test", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
//    public ResponseEntity<String> test(@RequestBody final Test test) {
//        System.out.println(test);
//        return ResponseEntity.ok(test.toString());
//    }