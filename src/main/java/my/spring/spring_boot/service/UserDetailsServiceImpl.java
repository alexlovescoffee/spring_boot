package my.spring.spring_boot.service;

import lombok.AllArgsConstructor;
import my.spring.spring_boot.model.User;
import my.spring.spring_boot.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user != null)
            return user;
        else
            throw new UsernameNotFoundException("User with email:" + username + " not found");
    }
}
