package com.tongtongbigboy.blog.service.admin;

import com.tongtongbigboy.blog.dto.cond.AdminCond;
import com.tongtongbigboy.blog.model.AdminDomain;

public interface AdminService {
    AdminDomain findAdminByCond(AdminCond adminCond);

    AdminDomain login(String username, String password);

    AdminDomain register(String username, String password);
}
