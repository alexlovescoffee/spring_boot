package my.spring.spring_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 По умолчанию SpringBoot устанавливает свойству spring.jpa.open-in-view значение true , то есть Spring автоматически
 выполняет транзакцию для каждого запроса.
 ToDo
 В следующей задаче добавить:
 - password encoder
 - dto
* */

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
