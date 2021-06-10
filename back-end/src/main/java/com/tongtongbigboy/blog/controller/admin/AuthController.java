package com.tongtongbigboy.blog.controller.admin;

import com.tongtongbigboy.blog.dto.Result;
import com.tongtongbigboy.blog.dto.cond.AdminCond;
import com.tongtongbigboy.blog.model.AdminDomain;
import com.tongtongbigboy.blog.service.admin.AdminService;
import com.tongtongbigboy.blog.service.log.LogService;
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
@RestController("adminAuthController")
@RequestMapping(value = "/admin")
@CrossOrigin
public class AuthController {

    private static final Logger LOGGER = LogManager.getLogger(AuthController.class);

    @Autowired
    private AdminService adminService;

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
            AdminCond adminCond = new AdminCond();
            adminCond.setUsername(username);
            AdminDomain admin0 = adminService.findAdminByCond(adminCond);
            if (admin0 == null) {
                return new Result(false, 4000,"管理员用户不存在");
            }else{
                return new Result(true,2000,"管理员用户存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            String msg = "管理员查询用户失败";
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
            return new Result(false, 4000, "管理员用户名或密码为空");
        }

        try {
            AdminDomain admin0 = adminService.login(username, password);
            if (admin0 == null) {
                return new Result(false, 4000,"管理员用户名或密码错误");
            }else{
                String token = jwtUtil.createJWT(String.valueOf(admin0.getAid()), admin0.getUsername(), "admin",admin0.getAid());
                Map<String,Object> data = new HashMap<>();
                data.put("token",token);
                data.put("role","admin");
                data.put("aid",admin0.getAid());
                data.put("username",admin0.getUsername());
                admin0.setPassword("null");
                data.put("admin",admin0);
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
            return new Result(false, 4000, "管理员用户名或密码为空");
        }
        try {
            AdminDomain admin0 = adminService.register(username, password);
        } catch (Exception e) {
            e.printStackTrace();
            String msg = "注册失败";
            return new Result(false, 4000, msg);
        }
        return new Result(true, 2000, "注册成功");
    }



}
