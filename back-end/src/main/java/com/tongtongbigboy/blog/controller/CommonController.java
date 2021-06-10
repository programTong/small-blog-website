package com.tongtongbigboy.blog.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import com.alibaba.fastjson.JSONObject;
import com.tongtongbigboy.blog.dto.Result;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@CrossOrigin
public class CommonController {

    @RequestMapping("/authCode")
    public void checkCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 自定义纯数字的验证码（随机4位数字，可重复）
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        lineCaptcha.setGenerator(randomGenerator);
        lineCaptcha.createCode();
        String code = lineCaptcha.getCode();
        request.getServletContext().setAttribute("CHECKCODE_SERVER",code);
        lineCaptcha.write(response.getOutputStream());
//        ImageIO.write(image,"PNG",);
    }

    @GetMapping("/checkAuthcode/{authCode}")
    public Result checkAuthcode(@PathVariable String authCode, HttpServletRequest request){
        Object checkcode_server = request.getServletContext().getAttribute("CHECKCODE_SERVER");
        if (checkcode_server == null || ((String)checkcode_server).length() == 0) {
            return new Result(false, 4000,"服务器验证码为空");
        }
        if (authCode == null || authCode.length() == 0) {
            return new Result(false, 4000,"验证码为空");
        }
        JSONObject json = new JSONObject();
        if (authCode.equalsIgnoreCase(checkcode_server.toString())){
            json.put("authcode_corrected",true);
            return new Result(true, 2000,"验证码正确",json);
        }else{
            json.put("authcode_corrected",false);
            return new Result(false, 4000,"验证码不正确",json);
        }
    }

}
