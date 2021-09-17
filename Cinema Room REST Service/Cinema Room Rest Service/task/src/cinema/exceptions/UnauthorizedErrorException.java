package cinema.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UnauthorizedErrorException extends RuntimeException{
    public UnauthorizedErrorException(String cause) {
        super(cause);
    }
}
