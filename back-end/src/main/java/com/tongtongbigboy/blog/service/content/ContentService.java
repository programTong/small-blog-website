package com.tongtongbigboy.blog.service.content;

import com.tongtongbigboy.blog.dto.cond.ContentCond;
import com.tongtongbigboy.blog.model.ContentDomain;
import com.github.pagehelper.PageInfo;

/**
 * 文章服务层
 * Created by Donghua.Chen on 2018/4/29.
 */
public interface ContentService {

    /**
     * 添加文章
     * @param contentDomain
     * @return
     */
    void addArticle(ContentDomain contentDomain,Integer uid);

    /**
     * 根据编号删除文章
     * @param cid
     * @return
     */
    void deleteArticleById(Integer cid);

    /**
     * 更新文章
     * @param contentDomain
     * @return
     */
    void updateArticleById(ContentDomain contentDomain, Integer uid);

    /**
     * 更新分类
     * @param ordinal
     * @param newCatefory
     */
    void updateCategory(String ordinal, String newCatefory);



    /**
     * 添加文章点击量
     * @param content
     */
    void updateContentByCid(ContentDomain content);

    /**
     * 根据编号获取文章
     * @param cid
     * @return
     */
    ContentDomain getAtricleById(Integer cid);

    /**
     * 根据条件获取文章列表
     * @param contentCond
     * @return
     */
    PageInfo<ContentDomain> getArticlesByCond(ContentCond contentCond, Integer pageNum, Integer pageSize);

    /**
     * 获取最近的文章（只包含id和title）
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<ContentDomain> getRecentlyArticle(Integer pageNum, Integer pageSize);

    /**
     * 搜索文章
     * @param param
     * @param pageNun
     * @param pageSize
     * @return
     */
    PageInfo<ContentDomain> searchArticle(String param, Integer pageNun, Integer pageSize);
}
