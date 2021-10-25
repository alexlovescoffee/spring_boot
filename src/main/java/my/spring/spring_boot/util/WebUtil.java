package my.spring.spring_boot.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import my.spring.spring_boot.dto.UserDto;
import my.spring.spring_boot.model.Role;
import my.spring.spring_boot.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WebUtil {
    public String getListOfUsersLikeJson(List<User> userList) {
        JsonArray jsonUsers = new JsonArray();
        for (User user : userList) {
            JsonObject jsonUser = new JsonObject();
            jsonUser.addProperty("id", user.getId());
            //jsonUser.addProperty("name", user.getName());
            jsonUser.addProperty("password", user.getPassword());
            JsonArray roles = new JsonArray();
            for (Role role : user.getRoles())
                roles.add(role.getRole().replaceAll("ROLE_", ""));
            jsonUser.add("roles", roles);
            jsonUsers.add(jsonUser);
        }
        return jsonUsers.toString();
    }

    public User from_UserDto_ToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .age(userDto.getAge())
                .email(userDto.getEmail())
                .password(userDto.getPassword())//password encoder::encode
                .roles(userDto
                        .getRoles()
                        .stream()
                        .map(s -> "ROLE_" + s)
                        .map(Role::new)
                        .collect(Collectors.toSet()))
                .build();
    }

    public UserDto from_User_ToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .age(user.getAge())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user
                        .getRoles()
                        .stream()
                        .map(Role::getRole)
                        .map(s -> s.substring(5))
                        .collect(Collectors.toSet()))
                .build();
    }

}
