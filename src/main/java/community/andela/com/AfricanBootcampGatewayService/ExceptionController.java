package community.andela.com.AfricanBootcampGatewayService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler()
    public ResponseEntity<Error> handleMailError(Exception exception){
        return new ResponseEntity<>(new Error(exception.getMessage(), HttpStatus.FAILED_DEPENDENCY),HttpStatus.FAILED_DEPENDENCY);
    }
}
