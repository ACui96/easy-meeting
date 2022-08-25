package com.bocft.meeting.common;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * token拦截器
 */
public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求头中的token
        String token = request.getHeader("Authorization");

        HashMap<String, Object> map = new HashMap<>();
        try {
            JwtUtil.verity(token);  //验证token
            return true;  //放行请求
        }catch (SignatureVerificationException e){
            e.printStackTrace();
            map.put("msg", "无效签名！");
        }catch (TokenExpiredException e){
            e.printStackTrace();
            map.put("msg", "token过期！");
        }catch (AlgorithmMismatchException e){
            e.printStackTrace();
            map.put("msg", "token算法不匹配！");
        }catch (Exception e){
            e.printStackTrace();
            map.put("msg", "token无效！");
        }

        map.put("state", false);
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;

    }
}
