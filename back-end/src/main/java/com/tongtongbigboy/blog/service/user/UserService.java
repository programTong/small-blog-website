package com.tongtongbigboy.blog.service.user;

import com.tongtongbigboy.blog.dto.cond.UserCond;
import com.tongtongbigboy.blog.model.UserDomain;

import org.springframework.stereotype.Service;
/**
 * Created by Donghua.Chen on 2018/4/20.
 */
@Service
public interface UserService {

    /**
     * @Author: Donghua.Chen
     * @Description: 更改用户信息
     * @Date: 2018/4/20
     * @param user
     */
    Integer updateUserInfo(UserDomain user);

    /**
     * @Author: Donghua.Chen
     * @Description: 根据主键编号获取用户信息
     * @Date: 2018/4/20
     * @param uId 主键
     */
    UserDomain getUserInfoById(Integer uId);


    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    UserDomain login(String username, String password);

    UserDomain register(String username, String password);


    UserDomain findUserByCond(UserCond userCond);
}
