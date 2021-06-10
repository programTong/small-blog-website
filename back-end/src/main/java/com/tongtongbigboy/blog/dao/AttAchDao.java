package com.tongtongbigboy.blog.dao;

import com.tongtongbigboy.blog.dto.AttAchDto;
import com.tongtongbigboy.blog.model.AttAchDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Donghua.Chen on 2018/4/29.
 */
@Mapper
public interface AttAchDao {


    /**
     * 添加单个附件信息
     * @param attAchDomain
     * @return
     */
    Integer addAttAch(AttAchDomain attAchDomain);

    /**
     * 批量添加附件信息
     * @param list
     * @return
     */
    Integer batchAddAttAch(List<AttAchDomain> list);

    /**
     * 根据主键编号删除附件信息
     * @param id
     * @return
     */
    Integer deleteAttAch(Integer id);

    /**
     * 更新附件信息
     * @param attAchDomain
     * @return
     */
    Integer updateAttAch(AttAchDomain attAchDomain);

    /**
     * 根据主键获取附件信息
     * @param id
     * @return
     */
    AttAchDto getAttAchById(@Param("id") Integer id);

    /**
     * 获取所有的附件信息
     * @return
     */
    List<AttAchDto> getAtts();

    /**
     * 查找附件的数量
     * @return
     */
    Long getAttsCount();
}
