package com.jaygame.base.interceptor;

import JwtUtil.JwtUtil;
import ch.qos.logback.classic.Level;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

@Component
public class JwtInterceptor  implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("pass the Interceptor!!!!");
        String header = request.getHeader("Authorization");
        System.out.println("header" + header);
        //only if there is Authorization in header,then:
        if( (header!=null)){
            if(header.startsWith("Bearer ")){
                //get Token and authorization
                String token = header.substring(7);
                try{
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    if(roles != null && roles.equals("admin")){
                        request.setAttribute("claims_role", "admin");
                        request.setAttribute("claims_user", token);
                        request.setAttribute("claims_user_id",claims.getId());
                        request.setAttribute("claims_user_name",claims.getSubject());
                        request.setAttribute("token_expired", "false");
                    }
                    if(roles != null && roles.equals("user")){
                        request.setAttribute("claims_role", "user");
                        request.setAttribute("claims_user", token);
                        request.setAttribute("claims_user_id",claims.getId());
                        request.setAttribute("claims_user_name",claims.getSubject());
                        request.setAttribute("token_expired", "false");
                    }
                }catch( ExpiredJwtException e){
                    request.setAttribute("token_expired", "true");
                    System.out.println("token expired");
                } catch(Exception e){
                    throw new  Exception("Some other exception in JWT parsing ");
                }

            }
        }
        //System.out.println("end of Interceptor!!");
        return true;
    }

}
