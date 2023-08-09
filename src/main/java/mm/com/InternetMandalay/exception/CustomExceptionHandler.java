package mm.com.InternetMandalay.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomExceptionHandler{
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handlerNotFoundException(NotFoundException e, WebRequest request){
        return new ErrorResponse(ErrorCode.NOT_FOUND_EXCEPTION, e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ErrorResponse handleBadRequestException(BadRequestException e, WebRequest request){
        return new ErrorResponse(ErrorCode.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(LoginException.class)
    public ErrorResponse handleLoginException(LoginException e, WebRequest request){
        return new ErrorResponse(ErrorCode.LOGIN_EXCEPTION, e.getMessage());
    }

}
