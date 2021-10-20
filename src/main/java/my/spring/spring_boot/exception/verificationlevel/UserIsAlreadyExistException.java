package my.spring.spring_boot.exception.verificationlevel;

import my.spring.spring_boot.exception.servicelevel.DataNotSavedException;

public class UserIsAlreadyExistException extends DataNotSavedException {
    public UserIsAlreadyExistException(String message) {
        super(message);
    }
}
