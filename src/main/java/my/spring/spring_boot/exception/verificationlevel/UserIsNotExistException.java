package my.spring.spring_boot.exception.verificationlevel;

import my.spring.spring_boot.exception.servicelevel.DataNotSavedException;

public class UserIsNotExistException extends DataNotSavedException {
    public UserIsNotExistException(String message) {
        super(message);
    }
}
