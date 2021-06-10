package com.tongtongbigboy.blog.service.user.impl;

import com.tongtongbigboy.blog.constant.ErrorConstant;
import com.tongtongbigboy.blog.dao.UserDao;
import com.tongtongbigboy.blog.dto.cond.UserCond;
import com.tongtongbigboy.blog.service.user.UserService;
import com.tongtongbigboy.blog.model.UserDomain;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Donghua.Chen on 2018/4/20.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;//这里会报错，但是并不会影响


    @Transactional
    @Override
    public Integer updateUserInfo(UserDomain user) {
        if (null == user.getUid())
            throw new RuntimeException("用户编号不可能为空");
        return userDao.updateUserInfo(user);
    }

    @Override
    public UserDomain getUserInfoById(Integer uId) {
        return userDao.getUserInfoById(uId);
    }

    @Override
    public UserDomain login(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password))
            throw new RuntimeException(ErrorConstant.Auth.USERNAME_PASSWORD_IS_EMPTY);
        UserCond userCond = new UserCond();
        userCond.setUsername(username);
        userCond.setPassword(password);
        UserDomain user = userDao.getUserInfoByCond(userCond);
        if (null == user)
            throw new RuntimeException(ErrorConstant.Auth.USERNAME_PASSWORD_ERROR);

        return user;
    }

    @Override
    public UserDomain register(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password))
            throw new RuntimeException(ErrorConstant.Auth.USERNAME_PASSWORD_IS_EMPTY);
        UserCond userCond = new UserCond();
        userCond.setUsername(username);
        userCond.setPassword(password);
        Integer count = userDao.addUserByUsernameAndPwd(username,password);
        UserDomain user = userDao.getUserInfoByCond(userCond);
        if (null == user)
            throw new RuntimeException(ErrorConstant.Auth.USERNAME_PASSWORD_ERROR);

        return user;
    }

    @Override
    public UserDomain findUserByCond(UserCond userCond) {
        UserDomain user = userDao.getUserInfoByCond(userCond);
        return user;
    }
}
