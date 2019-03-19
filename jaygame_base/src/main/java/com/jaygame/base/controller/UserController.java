package com.jaygame.base.controller;

import JwtUtil.JwtUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jaygame.base.pojo.User;
import com.jaygame.base.service.UserService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpServletRequest request;  //get user authorization from client.

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public Result findUser(@RequestBody User user){

            user = userService.login(user);
//            System.out.println(user.getUsername() + " " + user.getPassword() + "" + user.getState());
            if (user == null) {
                return new Result(false, StatusCode.ERROR, "username or password error", null);
            }

            Integer usr_privacy_id = user.getUsr_privacy_id();
            String role = "user";
            if(usr_privacy_id.equals(2)){
                role = "admin";
            }
        //create jwt
        String token = jwtUtil.createJWT(user.getUser_id().toString(), user.getUsername(), role);
            Map<String, Object>  map = new HashMap<>();
            map.put("token",token);
            map.put("role", role);
            map.put("state", user.getState());
            map.put("country", user.getCountry());
            return new Result(true, StatusCode.OK, "username and password correct", map);
    }


    @GetMapping(value="/profile")
    public Result userProfile(){

        String token_expired = (String) request.getAttribute("token_expired");
        if("true".equals(token_expired)){
            return new Result(false, StatusCode.JWTEXPIRED,"JwtExpired");
        }
        User user = userService.userProfile();
        return new Result(true, StatusCode.OK, "username and password correct", user);
    }

    @PostMapping(value="/reg")
    public Result userRegister(@RequestBody User user){

        System.out.println("username"+user.getUsername());
        System.out.println("password"+user.getPassword());
        user.setUsr_privacy_id(1);

        boolean flag= userService.register(user);
        if(!flag){
            return new Result(false, StatusCode.ERROR, "username existed");
        }
        return new Result(true, StatusCode.OK, "register success");

    }

    @PostMapping(value="/update")
    public Result userUpdate(@RequestBody User user){

        String token_expired = (String) request.getAttribute("token_expired");
        if("true".equals(token_expired)){
            return new Result(false, StatusCode.JWTEXPIRED,"JwtExpired");
        }

        boolean flag= userService.update(user);
        if(!flag){
            return new Result(false, StatusCode.ERROR, "update failed");
        }
        return new Result(true, StatusCode.OK, "register success");

    }

}
