package cinema.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException exception, WebRequest request){
        Error error = new Error(exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UnauthorizedErrorException.class)
    public ResponseEntity<?> handleUnauthorizedErrorException(UnauthorizedErrorException exception, WebRequest request){
        Error error = new Error(exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }


}
