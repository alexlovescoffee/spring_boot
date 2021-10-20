package my.spring.spring_boot.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import my.spring.spring_boot.model.Role;
import my.spring.spring_boot.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WebUtil {
    public String getListOfUsersLikeJson(List<User> userList) {
        JsonArray jsonUsers = new JsonArray();
        for (User user : userList) {
            JsonObject jsonUser = new JsonObject();
            jsonUser.addProperty("id", user.getId());
            jsonUser.addProperty("name", user.getName());
            jsonUser.addProperty("password", user.getPassword());
            JsonArray roles = new JsonArray();
            for (Role role : user.getRoles())
                roles.add(role.getRole().replaceAll("ROLE_", ""));
            jsonUser.add("roles", roles);
            jsonUsers.add(jsonUser);
        }
        return jsonUsers.toString();
    }
}
