package my.spring.spring_boot.exception.verificationlevel;

import my.spring.spring_boot.exception.servicelevel.DataNotSavedException;

public class RoleNotExistException extends DataNotSavedException {
    public RoleNotExistException(String message) {
        super(message);
    }
}
