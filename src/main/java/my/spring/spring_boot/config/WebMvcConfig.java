package my.spring.spring_boot.config;

import com.connect_group.thymeleaf_extras.ThymeleafExtrasDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public ThymeleafExtrasDialect thymeleafExtrasDialect() {
        return new ThymeleafExtrasDialect();
    }
}
