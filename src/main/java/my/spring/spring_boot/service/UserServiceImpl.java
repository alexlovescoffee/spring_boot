package my.spring.spring_boot.service;

import lombok.AllArgsConstructor;
import my.spring.spring_boot.exception.servicelevel.DataNotSavedException;
import my.spring.spring_boot.model.User;
import my.spring.spring_boot.repository.UserRepository;
import my.spring.spring_boot.service.verification.VerificationService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final VerificationService verificationService;

    @Override
    public void addUser(User user) throws DataNotSavedException {
        verificationService
                .userMustNotExistByName(user.getName())
                .terminalMergeRoles(user);
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) throws DataNotSavedException {
        verificationService
                .userMustExistById(user.getId())
                .userMustNotExistByNameExceptHisOwn(user.getName(), user.getId())
                .terminalMergeRoles(user);
        userRepository.save(user);
    }

    @Override
    public void deleteUserById(long id) throws DataNotSavedException {
        verificationService.userMustExistById(id);
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(long id) {
        return userRepository.getById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public User getUserByUsername(String name) {
        return userRepository.findByName(name);
    }
}
