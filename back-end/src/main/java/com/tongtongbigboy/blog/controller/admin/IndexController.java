package com.tongtongbigboy.blog.controller.admin;

import com.tongtongbigboy.blog.dto.Result;
import com.tongtongbigboy.blog.dto.StatisticsDto;
import com.tongtongbigboy.blog.model.UserDomain;
import com.tongtongbigboy.blog.service.log.LogService;
import com.tongtongbigboy.blog.service.site.SiteService;
import com.tongtongbigboy.blog.service.user.UserService;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 *后台首页
 */

@RestController("adminIndexController")
@CrossOrigin
@RequestMapping(value = "/admin")
public class IndexController  {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private SiteService siteService;

    @Autowired
    private LogService logService;

    @Autowired
    private UserService userService;


    @GetMapping(value = {"","/index"})
    public Result index(HttpServletRequest request){
        Claims user_claims = (Claims)request.getAttribute("admin_claims");
        if (user_claims==null){
            return new Result(false, 4000,"未登录");
        }

        LOGGER.info("Enter user index method");
        UserDomain user = new UserDomain();
        StatisticsDto statistics = siteService.getStatistics(user);
        LOGGER.info("Exit user index method");
        Map<String,Object> map = new HashMap<>();
        map.put("statistics",statistics);
        return new Result(true, 2000, "进入首页",map);
    }



}
