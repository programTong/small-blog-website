package com.tongtongbigboy.blog.controller.user;

import com.tongtongbigboy.blog.dto.Result;
import com.tongtongbigboy.blog.dto.cond.UserCond;
import com.tongtongbigboy.blog.model.UserDomain;
import com.tongtongbigboy.blog.service.log.LogService;
import com.tongtongbigboy.blog.service.user.UserService;
import com.tongtongbigboy.blog.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *登录相关接口
 */
@RestController
@RequestMapping(value = "/user")
@CrossOrigin
public class AuthController {

    private static final Logger LOGGER = LogManager.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private LogService logService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping(value = "/findByUserName")
    public Result findUserByUsername(
            HttpServletRequest request,
            @RequestParam(name = "username", required = true)
                    String username
    ){

        try {
            UserCond userCond = new UserCond();
            userCond.setUsername(username);
            UserDomain user0 = userService.findUserByCond(userCond);
            if (user0 == null) {
                return new Result(false, 4000,"用户不存在");
            }else{
                return new Result(true,2000,"用户存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            String msg = "查询用户失败";
            return new Result(false, 4000, msg);
        }
    }


    @PostMapping(value = "/login")
    public Result toLogin(
            HttpServletRequest request, @RequestBody Map<String,Object> map
    ){
        String username = (String)map.get("username");
        String password = (String)map.get("password");
        if (username==null||username.length()==0||password==null||password.length()==0){
            return new Result(false, 4000, "用户名或密码为空");
        }

        try {
            UserDomain user0 = userService.login(username, password);
            if (user0 == null) {
                return new Result(false, 4000,"用户名或密码错误");
            }else{
                String token = jwtUtil.createJWT(String.valueOf(user0.getUid()), user0.getUsername(), "user",user0.getUid());
                System.out.println("jwtUtil.getKey() = " + jwtUtil.getKey());
                Map<String,Object> data = new HashMap<>();
                data.put("token",token);
                data.put("role","user");
                data.put("uid",user0.getUid());
                data.put("username",user0.getUsername());
                user0.setPassword("null");
                data.put("user",user0);
                return new Result(true,2000,"登录成功",data);
            }

        } catch (Exception e) {
            e.printStackTrace();
            String msg = "登录失败";
            return new Result(false, 4000, msg);
        }
    }

    @ApiOperation("注册")
    @PostMapping(value = "/register")
    @ResponseBody
    public Result toRegister(HttpServletRequest request,
                                  HttpServletResponse response,
                             @RequestBody Map<String,Object> map
    ){
        String username = (String)map.get("username");
        String password = (String)map.get("password");
        if (username==null||username.length()==0||password==null||password.length()==0){
            return new Result(false, 4000, "用户名或密码为空");
        }
        try {
            UserDomain userInfo = userService.register(username, password);
        } catch (Exception e) {
            e.printStackTrace();
            String msg = "注册失败";
            return new Result(false, 4000, msg);
        }
        return new Result(true, 2000, "注册成功");
    }



}
