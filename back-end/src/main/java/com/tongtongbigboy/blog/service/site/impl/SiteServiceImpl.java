package com.tongtongbigboy.blog.service.site.impl;

import com.github.pagehelper.PageHelper;
import com.tongtongbigboy.blog.constant.ErrorConstant;
import com.tongtongbigboy.blog.constant.Types;
import com.tongtongbigboy.blog.constant.WebConst;
import com.tongtongbigboy.blog.dao.CommentDao;
import com.tongtongbigboy.blog.dao.ContentDao;
import com.tongtongbigboy.blog.dao.MetaDao;
import com.tongtongbigboy.blog.dto.ArchiveDto;
import com.tongtongbigboy.blog.dto.MetaDto;
import com.tongtongbigboy.blog.dto.StatisticsDto;
import com.tongtongbigboy.blog.dto.cond.CommentCond;
import com.tongtongbigboy.blog.dto.cond.ContentCond;
import com.tongtongbigboy.blog.model.CommentDomain;
import com.tongtongbigboy.blog.model.ContentDomain;
import com.tongtongbigboy.blog.model.UserDomain;
import com.tongtongbigboy.blog.service.site.SiteService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 站点服务
 *
 */
@Service
public class SiteServiceImpl implements SiteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SiteServiceImpl.class);

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private ContentDao contentDao;

    @Autowired
    private MetaDao metaDao;


    @Override
    public List<CommentDomain> getComments(Integer page_size, UserDomain user) {
        LOGGER.debug("Enter recentComments method:page_size={}", page_size);
        if (page_size < 0 || page_size > 10){
            page_size = 10;
        }
        PageHelper.startPage(1, page_size);
        CommentCond commentCond = new CommentCond();
        commentCond.setOwnerId(user.getUid());
        List<CommentDomain> rs = commentDao.getCommentsByCond(commentCond);
        LOGGER.debug("Exit recentComments method");
        return rs;
    }

    @Override
    public List<ContentDomain> getNewArticles(Integer page_size, UserDomain user) {
        LOGGER.debug("Enter recentArticles method:page_size={}", page_size);
        if (page_size < 0 || page_size > 10)
            page_size = 10;
        PageHelper.startPage(1, page_size);
        ContentCond contentCond = new ContentCond();
        contentCond.setAuthorId(user.getUid());
        List<ContentDomain> rs = contentDao.getArticlesByCond(contentCond);
        LOGGER.debug("Exit recentArticles method");
        return rs;
    }

    @Override

    public CommentDomain getComment(Integer coid) {
        LOGGER.debug("Enter recentComment method");
        if (null == coid)
            throw new RuntimeException(ErrorConstant.Common.PARAM_IS_EMPTY);
        CommentDomain comment = commentDao.getCommentById(coid);
        LOGGER.debug("Exit recentComment method");
        return comment;
    }

    @Override
    public StatisticsDto getStatistics(UserDomain user) {
        LOGGER.debug("Enter recentStatistics method");
        //文章总数
        ContentCond contentCond = new ContentCond();

        Long artices = contentDao.getArticleCount(contentCond);

        CommentCond commentCond = new CommentCond();
        Long comments = commentDao.getCommentsCount(commentCond);

        Long links = metaDao.getMetasCountByType(Types.LINK.getType());
        StatisticsDto rs = new StatisticsDto();
        rs.setArticles(artices);
        rs.setComments(comments);
        rs.setLinks(links);

        LOGGER.debug("Exit recentStatistics method");
        return rs;
    }

    @Override

    public List<ArchiveDto> getArchivesSimple(ContentCond contentCond) {
        LOGGER.debug("Enter getArchives method");
        List<ArchiveDto> archives = contentDao.getArchive(contentCond);
        LOGGER.debug("Exit getArchives method");
        return archives;
    }





    @Override

    public List<MetaDto> getMetas(String type, String orderBy, Integer page_size) {
        LOGGER.debug("Enter metas method:type={},order={},page_size={}", type, orderBy, page_size);
        List<MetaDto> retList=null;
        if (StringUtils.isNotBlank(type)) {
            if(StringUtils.isBlank(orderBy)){
                orderBy = "count desc, a.mid desc";
            }
            if(page_size < 1 || page_size > WebConst.MAX_POSTS){
                page_size = 10;
            }
            Map<String, Object> paraMap = new HashMap<>();
            paraMap.put("type", type);
            paraMap.put("order", orderBy);
            paraMap.put("page_size", page_size);
            retList= metaDao.selectFromSql(paraMap);
        }
        LOGGER.debug("Exit metas method");
        return retList;
    }
}
