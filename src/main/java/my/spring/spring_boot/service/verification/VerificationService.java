package my.spring.spring_boot.service.verification;

import my.spring.spring_boot.exception.verificationlevel.RoleNotExistException;
import my.spring.spring_boot.exception.verificationlevel.UserIsAlreadyExistException;
import my.spring.spring_boot.exception.verificationlevel.UserIsNotExistException;
import my.spring.spring_boot.model.User;

public interface VerificationService {
    VerificationService userMustNotExistByName(String name) throws UserIsAlreadyExistException;
    VerificationService userMustExistById(long id) throws UserIsNotExistException;
    VerificationService userMustNotExistByNameExceptHisOwn(String name, long id) throws UserIsAlreadyExistException;
    void terminalMergeRoles(User user) throws RoleNotExistException;
}
