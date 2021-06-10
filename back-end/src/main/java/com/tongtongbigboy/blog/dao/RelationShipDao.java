package com.tongtongbigboy.blog.dao;

import com.tongtongbigboy.blog.model.RelationShipDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 中间表
 *
 */
@Mapper
public interface RelationShipDao {

    /**
     * 添加
     * @param relationShip
     * @return
     */
    Integer addRelationShip(RelationShipDomain relationShip);

    /**
     * 根据文章编号和meta编号删除关联
     * @param cid
     * @param mid
     * @return
     */
    Integer deleteRelationShipById(@Param("cid") Integer cid, @Param("mid") Integer mid);

    /**
     * 根据文章编号删除关联
     * @param cid
     * @return
     */
    Integer deleteRelationShipByCid(@Param("cid") Integer cid);

    /**
     * 根据meta编号删除关联
     * @param mid
     * @return
     */
    Integer deleteRelationShipByMid(@Param("mid") Integer mid);

    /**
     * 更新
     * @param relationShip
     * @return
     */
    Integer updateRelationShip(RelationShipDomain relationShip);

    /**
     * 根据文章主键获取关联
     * @param cid
     * @return
     */
    List<RelationShipDomain> getRelationShipByCid(@Param("cid") Integer cid);

    /**
     * 根据meta编号获取关联
     * @param mid
     * @return
     */
    List<RelationShipDomain> getRelationShipByMid(@Param("mid") Integer mid);

    /**
     * 获取数量
     * @param cid
     * @param mid
     * @return
     */
    Long getCountById(@Param("cid") Integer cid, @Param("mid") Integer mid);
}
