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

    @ExceptionHandler(ParsingJsonException.class)
    public ErrorResponse handleParsingJsonException(ParsingJsonException e, WebRequest request){
        return new ErrorResponse(ErrorCode.PARSING_JSON_EXCEPTION, e.getMessage());
    }

    @ExceptionHandler(MessageHubException.class)
    public ErrorResponse handleMessageHubException(MessageHubException e, WebRequest request){
        return new ErrorResponse(ErrorCode.MESSAGE_HUB_EXCEPTION, e.getMessage());
    }
}
