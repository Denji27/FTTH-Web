package mm.com.InternetMandalay.securityConfig.logging;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tungct
 */
@ControllerAdvice
public class CustomResponseBodyAdviceAdapter implements ResponseBodyAdvice<Object> {

    final
    ILogging iLogging;

    final
    HttpServletRequest httpServletRequest;

    public CustomResponseBodyAdviceAdapter(ILogging iLogging, HttpServletRequest httpServletRequest) {
        this.iLogging = iLogging;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        /*if (Objects.requireNonNull(methodParameter.getMethod()).getName().equals("handleBusinessException")
                || Objects.requireNonNull(methodParameter.getMethod()).getName().equals("validateException"))
            return true;
//        PostMapping postMapping = methodParameter.getMethodAnnotation(PostMapping.class);

        RequestMapping requestMapping = methodParameter.getMethodAnnotation(RequestMapping.class);
        if (requestMapping != null){
            if (Arrays.asList(requestMapping.method()).isEmpty()) return true;
            return Arrays.asList(requestMapping.method()).contains(RequestMethod.POST);
        }
        return false;*/
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        if (serverHttpRequest instanceof ServletServerHttpRequest &&
                serverHttpResponse instanceof ServletServerHttpResponse) {
            if (!httpServletRequest.getRequestURI().contains("prometheus")) {
                iLogging.logResponse(
                        ((ServletServerHttpRequest) serverHttpRequest).getServletRequest(),
                        ((ServletServerHttpResponse) serverHttpResponse).getServletResponse(), o);
            }
        }
        return o;
    }
}