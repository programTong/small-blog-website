package com.tongtongbigboy.blog.service.content.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongtongbigboy.blog.constant.ErrorConstant;
import com.tongtongbigboy.blog.constant.Types;
import com.tongtongbigboy.blog.constant.WebConst;
import com.tongtongbigboy.blog.dao.CommentDao;
import com.tongtongbigboy.blog.dao.ContentDao;
import com.tongtongbigboy.blog.dao.RelationShipDao;
import com.tongtongbigboy.blog.dto.cond.ContentCond;
import com.tongtongbigboy.blog.model.CommentDomain;
import com.tongtongbigboy.blog.model.ContentDomain;
import com.tongtongbigboy.blog.model.RelationShipDomain;
import com.tongtongbigboy.blog.service.content.ContentService;
import com.tongtongbigboy.blog.service.meta.MetaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Donghua.Chen on 2018/4/29.
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentDao contentDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private MetaService metaService;

    @Autowired
    private RelationShipDao relationShipDao;


    @Transactional
    @Override
    public void addArticle(ContentDomain contentDomain,Integer uid) {
        if (null == contentDomain)
            throw new RuntimeException(ErrorConstant.Common.PARAM_IS_EMPTY);
        if (StringUtils.isBlank(contentDomain.getTitle()))
            throw new RuntimeException(ErrorConstant.Article.TITLE_CAN_NOT_EMPTY);
        if (contentDomain.getTitle().length() > WebConst.MAX_TITLE_COUNT)
            throw new RuntimeException(ErrorConstant.Article.TITLE_IS_TOO_LONG);
        if (StringUtils.isBlank(contentDomain.getContent()))
            throw new RuntimeException(ErrorConstant.Article.CONTENT_CAN_NOT_EMPTY);
        if (contentDomain.getContent().length() > WebConst.MAX_TEXT_COUNT)
            throw new RuntimeException(ErrorConstant.Article.CONTENT_IS_TOO_LONG);

        //标签和分类
        String tags = contentDomain.getTags();
        String categories = contentDomain.getCategories();

        contentDao.addArticle(contentDomain);

        Integer cid = contentDomain.getCid();
        metaService.addMetas(cid, tags, Types.TAG.getType(),uid);
        metaService.addMetas(cid, categories, Types.CATEGORY.getType(),uid);
    }

    @Override
    @Transactional
    public void deleteArticleById(Integer cid) {
        if (null == cid)
            throw new RuntimeException(ErrorConstant.Common.PARAM_IS_EMPTY);
        contentDao.deleteArticleById(cid);
        //同时也要删除该文章下的所有评论
        List<CommentDomain> comments = commentDao.getCommentsByCId(cid);
        if (null != comments && comments.size() > 0){
            comments.forEach(comment ->{
                commentDao.deleteComment(comment.getCoid());
            });
        }
        //删除标签和分类关联
        List<RelationShipDomain> relationShips = relationShipDao.getRelationShipByCid(cid);
        if (null != relationShips && relationShips.size() > 0){
            relationShipDao.deleteRelationShipByCid(cid);
        }

    }

    @Override
    @Transactional
    public void updateArticleById(ContentDomain contentDomain, Integer uid) {
        //标签和分类
        String tags = contentDomain.getTags();
        String categories = contentDomain.getCategories();

        contentDao.updateArticleById(contentDomain);
        Integer cid = contentDomain.getCid();
        relationShipDao.deleteRelationShipByCid(cid);
        metaService.addMetas(cid, tags, Types.TAG.getType(),uid);
        metaService.addMetas(cid, categories, Types.CATEGORY.getType(),uid);

    }

    @Override
    @Transactional
    public void updateCategory(String ordinal, String newCatefory) {
        ContentCond cond = new ContentCond();
        cond.setCategory(ordinal);
        List<ContentDomain> atricles = contentDao.getArticlesByCond(cond);
        atricles.forEach(atricle -> {
            atricle.setCategories(atricle.getCategories().replace(ordinal, newCatefory));
            contentDao.updateArticleById(atricle);
        });
    }



    @Override
    public void updateContentByCid(ContentDomain content) {
        if (null != content && null != content.getCid()) {
            contentDao.updateArticleById(content);
        }
    }

    @Override
    public ContentDomain getAtricleById(Integer cid) {
        if (null == cid)
            throw new RuntimeException(ErrorConstant.Common.PARAM_IS_EMPTY);
        return contentDao.getArticleById(cid);
    }

    @Override
    public PageInfo<ContentDomain> getArticlesByCond(ContentCond contentCond, Integer pageNum, Integer pageSize) {
        if (null == contentCond)
            throw new RuntimeException(ErrorConstant.Common.PARAM_IS_EMPTY);
        PageHelper.startPage(pageNum, pageSize);
        List<ContentDomain> contents = contentDao.getArticlesByCond(contentCond);
        PageInfo<ContentDomain> pageInfo = new PageInfo<>(contents);
        return pageInfo;
    }

    @Override
    public PageInfo<ContentDomain> getRecentlyArticle(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ContentDomain> recentlyArticle = contentDao.getRecentlyArticle();
        PageInfo<ContentDomain> pageInfo = new PageInfo<>(recentlyArticle);
        return pageInfo;
    }

    @Override
    public PageInfo<ContentDomain> searchArticle(String param, Integer pageNun, Integer pageSize) {
        PageHelper.startPage(pageNun,pageSize);
        List<ContentDomain> contentDomains = contentDao.searchArticle(param);
        PageInfo<ContentDomain> pageInfo = new PageInfo<>(contentDomains);
        return pageInfo;
    }
}
