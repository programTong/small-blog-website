package com.tongtongbigboy.blog.dao;

import com.tongtongbigboy.blog.dto.cond.AdminCond;
import com.tongtongbigboy.blog.model.AdminDomain;

public interface AdminDao {
    AdminDomain findByCond(AdminCond adminCond);

    int addAdmin(AdminDomain adminDomain);
}
