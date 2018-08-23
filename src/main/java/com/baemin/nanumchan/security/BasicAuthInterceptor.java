package com.baemin.nanumchan.security;

import com.baemin.nanumchan.domain.User;
import com.baemin.nanumchan.dto.LoginDTO;
import com.baemin.nanumchan.exception.UnAuthenticationException;
import com.baemin.nanumchan.service.UserService;
import com.baemin.nanumchan.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.Base64;

public class BasicAuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Basic")) {
            return true;
        }

        String base64Credentials = authorization.substring("Basic".length()).trim();
        String credentials = new String(Base64.getDecoder().decode(base64Credentials), Charset.forName("UTF-8"));

        final String[] values = credentials.split(":", 2);

        try {
            User user = userService.login(
                    LoginDTO.builder().email(values[0]).password(values[1]).build()
            );

            request.getSession().setAttribute(SessionUtils.USER_SESSION_KEY, user);
            return true;

        } catch (UnAuthenticationException e) {
            return true;
        }
    }
}
