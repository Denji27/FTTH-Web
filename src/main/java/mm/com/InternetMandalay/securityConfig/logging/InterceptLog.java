package mm.com.InternetMandalay.securityConfig.logging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : Tungct
 * @since : 5/14/2023, Sun
 **/
@Component
public class InterceptLog implements HandlerInterceptor {

    @Autowired
    ILogging loggingService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        loggingService.logRequest(request,null);

        /*if(request.getMethod().equals(HttpMethod.GET.name())
                || request.getMethod().equals(HttpMethod.DELETE.name())
                || request.getMethod().equals(HttpMethod.PUT.name()))    {
            loggingService.logRequest(request,null);
        }*/
        return true;
    }
}
