package com.tongtongbigboy.blog.controller.admin;

import com.github.pagehelper.PageInfo;
import com.tongtongbigboy.blog.dto.Result;
import com.tongtongbigboy.blog.dto.cond.ContentCond;
import com.tongtongbigboy.blog.model.ContentDomain;
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
import java.util.Map;

/**
 * 文章管理
 *
 */

@RestController("adminArticleController")
@RequestMapping("/admin/article")
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
        Claims user_claims = (Claims)request.getAttribute("admin_claims");
        if (user_claims==null){
            return new Result(false, 4000,"未登录");
        }
        ContentCond contentCond = new ContentCond();
        PageInfo<ContentDomain> articles = contentService.getArticlesByCond(contentCond, page, page_size);
        return new Result(true, 2000, "管理员文章首页成功",articles);
    }



    @PostMapping("/delete")
    public Result deleteArticle(
            HttpServletRequest request,
            @RequestBody Map<String,Object> map
    ){
        Claims user_claims = (Claims)request.getAttribute("admin_claims");
        if (user_claims==null){
            return new Result(false, 4000,"未登录");
        }

        Integer cid = (Integer)map.get("cid");
            contentService.deleteArticleById(cid);
        return new Result(true, 2000, "删除文章成功");
    }
}
