package com.tongtongbigboy.blog.service.site;

import com.tongtongbigboy.blog.dto.ArchiveDto;
import com.tongtongbigboy.blog.dto.MetaDto;
import com.tongtongbigboy.blog.dto.StatisticsDto;
import com.tongtongbigboy.blog.dto.cond.ContentCond;
import com.tongtongbigboy.blog.model.CommentDomain;
import com.tongtongbigboy.blog.model.ContentDomain;
import com.tongtongbigboy.blog.model.UserDomain;

import java.util.List;

/**
 * 站点服务
 *
 */
public interface SiteService {

    /**
     * 获取评论列表
     * @param page_size
     * @param user
     * @return
     */
    List<CommentDomain> getComments(Integer page_size, UserDomain user);

    /**
     * 获取最新的文章
     * @param page_size
     * @return
     */
    List<ContentDomain> getNewArticles(Integer page_size, UserDomain user);

    /**
     * 获取单条评论
     * @param coid
     * @return
     */
    CommentDomain getComment(Integer coid);

    /**
     * 获取 后台统计数据
     * @return
     * @param user
     */
    StatisticsDto getStatistics(UserDomain user);

    /**
     * 获取归档列表 - 只是获取日期和数量
     * @param contentCond
     * @return
     */
    List<ArchiveDto> getArchivesSimple(ContentCond contentCond);



    /**
     * 获取分类/标签列表
     * @param type
     * @param orderBy
     * @param page_size
     * @return
     */
    List<MetaDto> getMetas(String type, String orderBy, Integer page_size);
}
