package com.green.cloud.Interceptor;

import com.green.cloud.common.UserHolder;
import com.green.cloud.entity.UserAuth;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class UserDtoInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        UserAuth user = UserHolder.getUser();
        if (user == null){
            response.setStatus(401);
            return false;
        }

        return true;

    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        // 没有做UserHolder的清空处理
    }
}
