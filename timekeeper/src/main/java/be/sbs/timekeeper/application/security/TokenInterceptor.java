package be.sbs.timekeeper.application.security;

import be.sbs.timekeeper.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private UserService userService;

    private final List<String> PATHS_WITHOUT_TOKEN = Arrays.asList("/login", "/user/register", "/activate", "/error", "/captcha");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!PATHS_WITHOUT_TOKEN.contains(request.getRequestURI())) {
            String token = request.getHeader("Token");

            if (!userService.userAuthenticated(token)) {
                response.setStatus(401);
                response.getWriter().write("Token not valid");
                response.getWriter().flush();
                response.getWriter().close();
            }
        }
        
        return super.preHandle(request, response, handler);
    }


}
