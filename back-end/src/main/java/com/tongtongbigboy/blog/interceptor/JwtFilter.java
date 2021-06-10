package com.tongtongbigboy.blog.interceptor;

import com.tongtongbigboy.blog.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtFilter implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null &&
                authHeader.startsWith("user ")) {
            final String token = authHeader.substring(5); // The part
            try {
                Claims claims = jwtUtil.parseJWT(token);
                if (claims != null) {
                    if("admin".equals(claims.get("roles"))){//如果是管理员
                        request.setAttribute("admin_claims", claims);
                    }
                    if("user".equals(claims.get("roles"))){//如果是用户
                        request.setAttribute("user_claims", claims);
                    }
                }
            }catch (Exception e){
                throw new RuntimeException("令牌不正确");
            }
        }
        return true;
    }
}
