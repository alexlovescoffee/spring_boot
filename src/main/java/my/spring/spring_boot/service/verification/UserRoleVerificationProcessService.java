package my.spring.spring_boot.service.verification;

import lombok.AllArgsConstructor;
import my.spring.spring_boot.exception.verificationlevel.RoleNotExistException;
import my.spring.spring_boot.exception.verificationlevel.UserIsAlreadyExistException;
import my.spring.spring_boot.exception.verificationlevel.UserIsNotExistException;
import my.spring.spring_boot.model.Role;
import my.spring.spring_boot.model.User;
import my.spring.spring_boot.repository.RoleRepository;
import my.spring.spring_boot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserRoleVerificationProcessService implements VerificationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public VerificationService userMustNotExistByEmail(String email) throws UserIsAlreadyExistException {
        if (userRepository.findByEmail(email) != null)
            throw new UserIsAlreadyExistException("User with email: " + email + " is already exist");
        return this;
    }

    @Override
    public VerificationService userMustExistById(long id) throws UserIsNotExistException {
        if (!userRepository.existsById(id))
            throw new UserIsNotExistException("User with id: " + id + " is not exist");
        return this;
    }

    @Override
    public VerificationService userMustNotExistByEmailExceptHisOwn(String email, long id) throws UserIsAlreadyExistException {
        if (userRepository.findByEmailAndIdNot(email, id) != null)
            throw new UserIsAlreadyExistException("User with name: " + email + " is already exist");
        return this;
    }

    @Override
    public void terminalMergeRoles(User user) throws RoleNotExistException {
        if (user.getRoles().size() == 0)
            throw new RoleNotExistException("Role of user is empty");

        List<Role> roles = new ArrayList<>(user.getRoles());
        for (Role role : roles) {
            Role existingRole;
            if ((existingRole = roleRepository.findByRole(role.getRole())) != null)
                role.setId(existingRole.getId());
            else
                throw new RoleNotExistException("Role with name: " + role.getRole() + " is not exist");
        }

    }
}
