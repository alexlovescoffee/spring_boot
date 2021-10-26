package my.spring.spring_boot.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import my.spring.spring_boot.dto.jsonview.OnAdd;
import my.spring.spring_boot.dto.jsonview.OnEdit;
import my.spring.spring_boot.dto.jsonview.OnGet;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

/*использовать spring validation
  логика проверки также должна находиться в этом слое
* */
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder @ToString
public class UserDto {
    @JsonView({OnGet.class, OnEdit.class})
    private long id;

    @JsonView({OnGet.class, OnAdd.class, OnEdit.class})
    @NotBlank(groups = {OnGet.class, OnAdd.class, OnEdit.class})
    private String firstName;

    @JsonView({OnGet.class, OnAdd.class, OnEdit.class})
    @NotBlank(groups = {OnGet.class, OnAdd.class, OnEdit.class})
    private String lastName;

    @JsonView({OnGet.class, OnAdd.class, OnEdit.class})
    @Min(value = 1, groups = {OnGet.class, OnAdd.class, OnEdit.class})
    private int age;

    @JsonView({OnGet.class, OnAdd.class, OnEdit.class})
    @NotBlank(groups = {OnGet.class, OnAdd.class, OnEdit.class})
    private String email;

    @JsonView({OnAdd.class, OnEdit.class})
    @NotBlank(groups = {OnAdd.class, OnEdit.class})
    private String password;

    @JsonView({OnGet.class, OnAdd.class, OnEdit.class})
    @NotEmpty(groups = {OnGet.class, OnAdd.class, OnEdit.class})
    private Set<String> roles;

}