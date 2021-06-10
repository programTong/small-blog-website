package com.tongtongbigboy.blog.controller.user;

import com.github.pagehelper.PageInfo;
import com.tongtongbigboy.blog.constant.Types;
import com.tongtongbigboy.blog.dto.Result;
import com.tongtongbigboy.blog.dto.cond.ContentCond;
import com.tongtongbigboy.blog.dto.cond.MetaCond;
import com.tongtongbigboy.blog.model.ContentDomain;
import com.tongtongbigboy.blog.model.MetaDomain;
import com.tongtongbigboy.blog.service.content.ContentService;
import com.tongtongbigboy.blog.service.log.LogService;
import com.tongtongbigboy.blog.service.meta.MetaService;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文章管理
 *
 */

@RestController
@RequestMapping("/user/article")
@Transactional
@CrossOrigin
public class ArticleController  {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private ContentService contentService;

    @Autowired
    private MetaService metaService;

    @Autowired
    private LogService logService;

    /**
     * 用户管理文章页
     * @param request
     * @param page
     * @param page_size
     * @return
     */
    @GetMapping(value = "")
    public Result index(
            HttpServletRequest request,
            @RequestParam(name = "page", required = false, defaultValue = "1")
            Integer page,
            @RequestParam(name = "page_size", required = false, defaultValue = "15")
            Integer page_size
    ){
        Claims user_claims = (Claims)request.getAttribute("user_claims");
        if (user_claims==null){
            return new Result(false, 4000,"未登录");
        }
        System.out.println("page = " + page);
        System.out.println("page_size = " + page_size);
        ContentCond contentCond = new ContentCond();
        contentCond.setAuthorId((Integer) user_claims.get("uid"));
        PageInfo<ContentDomain> articles = contentService.getArticlesByCond(contentCond, page, page_size);
        return new Result(true, 2000, "用户管文章理首页成功",articles);
    }

    /**
     * 发布文章页面分类数据
     * @param request
     * @return
     */
    @GetMapping(value = "/publish")
    public Result newArticle(HttpServletRequest request){
        Claims user_claims = (Claims)request.getAttribute("user_claims");
        if (user_claims==null){
            return new Result(false, 4000,"未登录");
        }
        MetaCond metaCond = new MetaCond();
        metaCond.setUid((Integer) user_claims.get("uid"));
        metaCond.setType(Types.CATEGORY.getType());
        List<MetaDomain> metas = metaService.getMetas(metaCond);
        Map<String,Object> map = new HashMap<>();
        map.put("categories",metas);
        return new Result(true, 2000, "发布文章页面分类数据成功",map);
    }

    /**
     *  发布新文章页面
     *
     */
    @PostMapping(value = "/publish")
    public Result publishArticle(
            HttpServletRequest request,
            @RequestBody Map<String,Object> map
            ){
        Claims user_claims = (Claims)request.getAttribute("user_claims");
        if (user_claims==null){
            return new Result(false, 4000,"未登录");
        }
        String title = (String)map.get("title");
        String titlePic = (String)map.get("titlePic");
        String slug = (String)map.get("slug");
        String content = (String)map.get("content");
        String type = (String)map.get("type");
        String status = (String)map.get("status");
        String tags = (String)map.get("tags");
        String categories = (String)map.get("categories");
        Boolean allowComment = (Boolean)map.get("allowComment");

        ContentDomain contentDomain = new ContentDomain();
        contentDomain.setTitle(title);
        contentDomain.setTitlePic(titlePic);
        contentDomain.setSlug(slug);
        contentDomain.setContent(content);
        contentDomain.setType(type);
        contentDomain.setStatus(status);
        contentDomain.setTags(type.equals(Types.ARTICLE.getType()) ? tags : null);
        //只允许博客文章有分类，防止作品被收入分类
        contentDomain.setCategories(type.equals(Types.ARTICLE.getType()) ? categories : null);
        contentDomain.setAllowComment(allowComment ? 1 : 0);
        contentDomain.setAuthorId((Integer) user_claims.get("uid"));
        contentDomain.setCreated(((int) System.currentTimeMillis()));
        contentService.addArticle(contentDomain,(Integer) user_claims.get("uid"));
        return new Result(true, 2000, "发布新文章页面成功");


    }



    @PostMapping("/modify")
    public Result modifyArticle(
            HttpServletRequest request,
            @RequestBody Map<String,Object> map
    ){
        Claims user_claims = (Claims)request.getAttribute("user_claims");
        if (user_claims==null){
            return new Result(false, 4000,"未登录");
        }

        Integer cid = (Integer)map.get("cid");
        String title = (String)map.get("title");
        String titlePic = (String)map.get("titlePic");
        String slug = (String)map.get("slug");
        String content = (String)map.get("content");
        String type = (String)map.get("type");
        String status = (String)map.get("status");
        String tags = (String)map.get("tags");
        String categories = (String)map.get("categories");
        Boolean allowComment = (Boolean)map.get("allowComment");
        ContentDomain contentDomain = new ContentDomain();
        contentDomain.setCid(cid);
        contentDomain.setTitle(title);
        contentDomain.setTitlePic(titlePic);
        contentDomain.setSlug(slug);
        contentDomain.setContent(content);
        contentDomain.setType(type);
        contentDomain.setStatus(status);
        contentDomain.setTags(tags);
        contentDomain.setCategories(categories);
        contentDomain.setAllowComment(allowComment ? 1 : 0);

        contentService.updateArticleById(contentDomain,(Integer) user_claims.get("uid"));
        return new Result(true, 2000, "文章编辑页成功");
    }


    @PostMapping("/delete")
    public Result deleteArticle(
            HttpServletRequest request,
            @RequestBody Map<String,Object> map
    ){
        Claims user_claims = (Claims)request.getAttribute("user_claims");
        if (user_claims==null){
            return new Result(false, 4000,"未登录");
        }

        Integer cid = (Integer)map.get("cid");
            contentService.deleteArticleById(cid);
        return new Result(true, 2000, "删除文章成功");
    }
}
