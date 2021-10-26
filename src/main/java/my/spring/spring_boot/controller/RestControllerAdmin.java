package my.spring.spring_boot.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import my.spring.spring_boot.dto.UserDto;
import my.spring.spring_boot.dto.jsonview.OnAdd;
import my.spring.spring_boot.dto.jsonview.OnEdit;
import my.spring.spring_boot.dto.jsonview.OnGet;
import my.spring.spring_boot.exception.servicelevel.DataNotSavedException;
import my.spring.spring_boot.model.User;
import my.spring.spring_boot.service.UserService;
import my.spring.spring_boot.service.UserDtoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("admin")
@Validated
public class RestControllerAdmin {

    private final UserService userService;
    private final UserDtoService userDtoService;

    @GetMapping("get-user")
    @JsonView(OnGet.class)
    public ResponseEntity<UserDto> getUser(@RequestParam("id") long id) {
        UserDto userDto = userDtoService.from_User_ToUserDto(userService.getUserById(id));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping
    @JsonView(OnGet.class)
    @Validated(OnAdd.class)
    public ResponseEntity<?> addUser(@Valid @RequestBody @JsonView(OnAdd.class) final UserDto userDto) {
        try {
            User newUser = userDtoService.from_UserDto_ToUser(userDto);
            userService.addUser(newUser);
            return ResponseEntity.ok(userDtoService.from_User_ToUserDto(newUser));
        } catch (DataNotSavedException e) {
            return ResponseEntity.badRequest().body("Error! User not added! Message: " + e.getMessage());
        }
    }

    @PutMapping
    @JsonView(OnGet.class)
    @Validated(OnEdit.class)
    public ResponseEntity<?> updateUser(@Valid @RequestBody @JsonView(OnEdit.class) final UserDto userDto,
                                        Authentication auth) {
        try {
            long currentUserId = userService.getUserByEmail(auth.getName()).getId();
            User updateUser = userDtoService.from_UserDto_ToUser(userDto);
            userService.updateUser(updateUser);

            //если пользователь изменил себя, то произойдет logout
            if (currentUserId == updateUser.getId()) {
                auth.setAuthenticated(false);
                return ResponseEntity.status(205).header("re-authenticate", "/logout").build();
            }

            return ResponseEntity.ok(userDtoService.from_User_ToUserDto(updateUser));
        } catch (DataNotSavedException e) {
            return ResponseEntity.badRequest().body("Error! User not updated! Message: " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestParam("id") long id, Authentication auth) {
        try {
            long currentUserId = userService.getUserByEmail(auth.getName()).getId();
            userService.deleteUserById(id);

            //если пользователь удалил себя, то произойдет logout
            if (currentUserId == id)
                return ResponseEntity.status(205).header("re-authenticate", "/logout").build();

            return ResponseEntity.ok(id);
        } catch (DataNotSavedException e) {
            return ResponseEntity.badRequest().body("Error! User not deleted! Message: " + e.getMessage());
        }
    }
}
