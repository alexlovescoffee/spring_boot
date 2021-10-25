package my.spring.spring_boot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<String> onConstraintValidationException(ConstraintViolationException e) {
        StringBuilder errorMsg = new StringBuilder();
        for (ConstraintViolation<?> violation : e.getConstraintViolations())
            errorMsg.append(violation.getPropertyPath().toString().substring(violation.getPropertyPath().toString().lastIndexOf(".") + 1))
                    .append(" ")
                    .append(violation.getMessage())
                    .append(System.lineSeparator());
        return ResponseEntity.badRequest().body(errorMsg.toString());
    }
}
