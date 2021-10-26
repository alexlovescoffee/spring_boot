package my.spring.spring_boot.service;

import lombok.AllArgsConstructor;
import my.spring.spring_boot.dto.UserDto;
import my.spring.spring_boot.model.Role;
import my.spring.spring_boot.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDtoService {

    private final PasswordEncoder passwordEncoder;

    public User from_UserDto_ToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .age(userDto.getAge())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
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
                .password(user.getPassword())//правильная настройка @JsonView позволяет никогда не возвращать клиенту в dto пароль
                .roles(user
                        .getRoles()
                        .stream()
                        .map(Role::getRole)
                        .map(s -> s.substring(5))
                        .collect(Collectors.toSet()))
                .build();
    }
}