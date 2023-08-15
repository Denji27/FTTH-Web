package mm.com.InternetMandalay.securityConfig.logging;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;

/**
 * @author Tungct
 */
@ControllerAdvice
//@RestControllerAdvice
public class CustomRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {
    private final Logger logger = LoggerFactory.getLogger(CustomRequestBodyAdviceAdapter.class);

    final
    HttpServletRequest httpServletRequest;

    final
    ILogging iLogging;

    public CustomRequestBodyAdviceAdapter(HttpServletRequest httpServletRequest, ILogging iLogging) {
        this.httpServletRequest = httpServletRequest;
        this.iLogging = iLogging;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type type,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @SneakyThrows
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage,
                                MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {
        if (!httpServletRequest.getRequestURI().contains("prometheus"))
            iLogging.logRequest(httpServletRequest, body);
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
}