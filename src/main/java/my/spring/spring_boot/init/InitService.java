package my.spring.spring_boot.init;

import lombok.AllArgsConstructor;
import my.spring.spring_boot.model.Role;
import my.spring.spring_boot.model.User;
import my.spring.spring_boot.repository.RoleRepository;
import my.spring.spring_boot.repository.UserRepository;
import my.spring.spring_boot.service.UserService;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
@AllArgsConstructor
public class InitService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        System.out.println("handleContextRefresh");
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser =  new Role("ROLE_USER");

        Set<Role> set = new HashSet<>();
        Collections.addAll(set, roleAdmin, roleUser);

        User user = new User(); user.setName("admin"); user.setPassword("test");
        user.setRoles(set);

        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);
        userRepository.save(user);
    }
}
