package com.tongtongbigboy.blog.dao;

import com.tongtongbigboy.blog.dto.cond.UserCond;
import com.tongtongbigboy.blog.model.UserDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Donghua.Chen on 2018/4/20.
 */
@Mapper
public interface UserDao {

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
    UserDomain getUserInfoById(@Param("uid") Integer uId);


    Integer addUserByUsernameAndPwd(String username, String password);

    UserDomain findUserByUsername(String username);

    UserDomain getUserInfoByCond(UserCond userCond);
}
