package my.spring.spring_boot;

import lombok.AllArgsConstructor;
import my.spring.spring_boot.model.Role;
import my.spring.spring_boot.model.User;
import my.spring.spring_boot.repository.RoleRepository;
import my.spring.spring_boot.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/*
 По умолчанию SpringBoot устанавливает свойству spring.jpa.open-in-view значение true , то есть Spring автоматически
 выполняет транзакцию для каждого запроса.
 ToDo
 В следующей задаче добавить:
 - password encoder
 - dto
* */

@SpringBootApplication
@AllArgsConstructor
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        System.out.println("handleContextRefresh");
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser =  new Role("ROLE_USER");

        Set<Role> set = new HashSet<>();
        Collections.addAll(set, roleAdmin, roleUser);

        User user = new User(1, "Alex", "Link", 26, "admin@gmail.com", "test", set);

        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);
        userRepository.save(user);
    }

//    @Bean
//    public ModelMapper modelMapper() {
//        return new ModelMapper();
//    }
}
