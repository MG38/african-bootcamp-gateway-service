package community.andela.com.AfricanBootcampGatewayService.exception.controller;

import community.andela.com.AfricanBootcampGatewayService.exception.dto.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Error> handleValidationError(Exception exception){
        return new ResponseEntity<>(new Error(exception.getMessage(), HttpStatus.FAILED_DEPENDENCY),HttpStatus.FAILED_DEPENDENCY);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Error> handleIllegalArgumentException(Exception exception){
        return new ResponseEntity<>(new Error(exception.getMessage(), HttpStatus.FAILED_DEPENDENCY), HttpStatus.FAILED_DEPENDENCY);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Error> handleNullPointerException(Exception exception){
        return new ResponseEntity<>(new Error(exception.getMessage(), HttpStatus.FAILED_DEPENDENCY), HttpStatus.FAILED_DEPENDENCY);
    }

    @ExceptionHandler(ClassCastException.class)
    public ResponseEntity<Error> handleClassCastException(Exception exception){
        return new ResponseEntity<>(new Error(exception.getMessage(), HttpStatus.FAILED_DEPENDENCY), HttpStatus.FAILED_DEPENDENCY);
    }
}
