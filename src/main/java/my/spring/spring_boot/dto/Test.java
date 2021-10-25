package my.spring.spring_boot.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import my.spring.spring_boot.dto.jsonview.OnGet;
import my.spring.spring_boot.dto.jsonview.OnTwo;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Test {
    @JsonView({OnGet.class, OnTwo.class}) private long id;
    @JsonView({OnGet.class, OnTwo.class}) private String firstName;
    @JsonView(OnGet.class) private String lastName;
}
