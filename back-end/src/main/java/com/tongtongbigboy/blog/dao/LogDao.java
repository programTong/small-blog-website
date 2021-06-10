package com.tongtongbigboy.blog.dao;

import com.tongtongbigboy.blog.model.LogDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Donghua.Chen on 2018/4/29.
 */
@Mapper
public interface LogDao {

    /**
     * 添加日志
     * @param logDomain
     * @return
     */
    Integer addLog(LogDomain logDomain);

    /**
     * 删除日志
     * @param id
     * @return
     */
    Integer deleteLogById(@Param("id") Integer id);

    /**
     * 获取日志
     * @return
     */
    List<LogDomain> getLogs();
}
