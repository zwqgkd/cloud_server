package com.green.cloud.Interceptor;

import com.green.cloud.common.UserHolder;
import com.green.cloud.entity.UserAuth;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("token");
        if (token == null){
            System.out.println("token为空");
            response.setStatus(401);
            return false;
        }

        HttpSession session = request.getSession();
        if (session == null){
            System.out.println("session为空");
            response.setStatus(401);
            return false;
        }

        UserAuth userAuth = (UserAuth) session.getAttribute(token);
        if (userAuth == null){
            System.out.println("session中用户信息不存在");
            response.setStatus(401);
            return false;
        }
        UserHolder.saveUser(userAuth);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }
}

