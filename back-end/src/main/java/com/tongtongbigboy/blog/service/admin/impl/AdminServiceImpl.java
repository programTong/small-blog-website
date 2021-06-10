package com.tongtongbigboy.blog.service.admin.impl;

import com.tongtongbigboy.blog.dao.AdminDao;
import com.tongtongbigboy.blog.dto.cond.AdminCond;
import com.tongtongbigboy.blog.model.AdminDomain;
import com.tongtongbigboy.blog.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Override
    public AdminDomain findAdminByCond(AdminCond adminCond) {
        AdminDomain adminDomain = adminDao.findByCond(adminCond);
        return adminDomain;
    }

    @Override
    public AdminDomain login(String username, String password) {
        AdminCond adminCond = new AdminCond();
        adminCond.setUsername(username);
        adminCond.setPassword(password);
        AdminDomain adminDomain = adminDao.findByCond(adminCond);
        return adminDomain;
    }

    @Override
    public AdminDomain register(String username, String password) {
        AdminDomain adminDomain = new AdminDomain();
        adminDomain.setUsername(username);
        adminDomain.setPassword(password);
        adminDomain.setRole("一级管理员");
        String time = String.valueOf(System.currentTimeMillis());
        adminDomain.setCreated(Integer.parseInt(time.substring(4,time.length())));
        int count = adminDao.addAdmin(adminDomain);

        AdminCond adminCond = new AdminCond();
        adminCond.setUsername(username);
        adminCond.setPassword(password);
        AdminDomain admin = adminDao.findByCond(adminCond);
        return admin;
    }
}
