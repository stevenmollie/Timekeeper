package be.sbs.timekeeper.application.security;

import be.sbs.timekeeper.application.service.UserService;
import com.sun.deploy.security.UserDeclinedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(!request.getRequestURI().contains("/login")) {
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
