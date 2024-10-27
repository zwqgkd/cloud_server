package com.green.cloud.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();	//输出 OPTIONS/GET/POST。。。
        //如果是 OPTIONS 请求，让 OPTIONS 请求返回一个200状态码
        System.out.println(method);
        if ("OPTIONS".equals(method)) {
            return true;
        }
        System.out.println("拦截器判断");


        String token = request.getHeader("token");

        System.out.println(token);

        if (token != null) {
            System.out.println(stringRedisTemplate.opsForValue().get(token));
            Object userInfo = stringRedisTemplate.opsForValue().get(token);
            if (userInfo != null) {
                HttpSession session = request.getSession();
                session.setAttribute("userInfo", userInfo);
                return true;
            }
        }
        System.out.println("Token拦截");
        response.setStatus(401);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
}
