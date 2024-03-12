package com.example.demo.interceptors;

import com.example.demo.pojo.Result;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    //令牌验证用request对象得到
        String token = request.getHeader("Authorization");


        try {
            //从redis中获取到相同的token
            ValueOperations<String, String> oprations = stringRedisTemplate.opsForValue();
            String redisToken = oprations.get(token);

            if(redisToken == null){
                throw new RuntimeException();
            }
            //解析token
            Map<String, Object> claims = JwtUtil.parseToken(token);

            //把业务数据存储到ThreadLocal中
            ThreadLocalUtil.set(claims);
            //放行
            return true;
        } catch (Exception e) {
            //失败了http响应状态码为401
            response.setStatus(401);
            //不放行
            return  false;
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空ThreadLocal的数据
        ThreadLocalUtil.remove();
    }
}
