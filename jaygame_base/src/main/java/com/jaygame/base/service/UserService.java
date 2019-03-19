package com.jaygame.base.service;

import JwtUtil.JwtUtil;
import com.jaygame.base.dao.UserDao;
import com.jaygame.base.pojo.User;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private HttpServletRequest request;  //get user authorization from client.
//    @Autowired
//    private JwtUtil jwtUtil;

    //token authorization, admin user
//    public Map<String, Object> userToken(){
//        Map<String, Object> map = new HashMap<>();
//        String header = request.getHeader("Authorization");   //user will pass format;" Authorization; Bearer 'token'";
//        if(header == null || "".equals(header) || !header.startsWith("Bearer ")){
//            throw new RuntimeException("not enough right");
//        }
//        String token = header.substring(7);
//        try{
//            Claims claims = jwtUtil.parseJWT(token);
//            String roles = (String)claims.get("roles");
//            String userId = claims.getId();
//            map.put("userId", userId);
//            map.put("roles", roles);
//            return map;
//           // if(roles==null || !roles.equals("admin")) return 1;
//        }catch (Exception e) {
//            throw new RuntimeException("not enough right");
//        }
//    }


    /**
     * 1.parse request attributes passed by interceptor
     * @return  User
     */
    public User userProfile(){
//        Map<String,Object> map = userToken();
//        String userId = (String)map.get("userId");

        User user = null;
        String token_expired = (String) request.getAttribute("token_expired");
        if(token_expired.equals("true")){
            return user;
        }
        String token = (String) request.getAttribute("claims_user");
        if(token ==null || "".equals(token)) throw new RuntimeException("please login first");
        else{
            String claims_user_id = (String) request.getAttribute("claims_user_id");
            user = userDao.getOne(Integer.parseInt(claims_user_id));

            System.out.println(claims_user_id + " " + user);
        }
       // User byUser_id = userDao.findByUser_id(Integer.parseInt(userId));

        return user;
    }

    public User login(User user){
        User userLogin =   userDao.findByUsername(user.getUsername());

        if(userLogin !=null){
            return userDao.findByPassword(user.getPassword());
        }
        return null;
    }

    public boolean register(User user){
      User user1=  userDao.findByUsername(user.getUsername());
      if(user1 !=null){
          return false;
      }else {
          User save = userDao.save(user);
          if(save ==null){
              return false;
          }
      }
        return true;
    }

    public boolean update(User user) {

        User user1 = null;
        String token_expired = (String) request.getAttribute("token_expired");
        if(token_expired.equals("true")){
            return false;
        }
        String token = (String) request.getAttribute("claims_user");
        if(token ==null || "".equals(token)) throw new RuntimeException("please login first");
        else {
            String claims_user_id = (String) request.getAttribute("claims_user_id");
            user1 = userDao.getOne(Integer.parseInt(claims_user_id));
        }

        user1.setUsername(user.getUsername());
        User save = userDao.save(user1);
        if(save ==null){
            return false;
        }
        return true;
    }
}
