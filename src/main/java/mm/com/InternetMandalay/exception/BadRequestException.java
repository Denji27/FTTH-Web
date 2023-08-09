package mm.com.InternetMandalay.exception;

import org.springframework.web.client.RestClientException;

public class BadRequestException extends RestClientException {
    public BadRequestException(String message){
        super(message);
    }
}
