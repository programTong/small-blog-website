package com.tongtongbigboy.blog.controller;

import com.github.pagehelper.PageInfo;
import com.tongtongbigboy.blog.dto.Result;
import com.tongtongbigboy.blog.dto.cond.ContentCond;
import com.tongtongbigboy.blog.model.CommentDomain;
import com.tongtongbigboy.blog.model.ContentDomain;
import com.tongtongbigboy.blog.model.UserDomain;
import com.tongtongbigboy.blog.service.comment.CommentService;
import com.tongtongbigboy.blog.service.content.ContentService;
import com.tongtongbigboy.blog.service.log.LogService;
import com.tongtongbigboy.blog.service.user.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class BlogSea {

    @Autowired
    private ContentService contentService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private LogService logService;

    @GetMapping("/article/comment")
    public Result articleComment(
            @RequestParam(name = "cid", required = true)
                    Integer cid
    ){
        try {
            List<CommentDomain> comments = commentService.getCommentsByCId(cid);
            return new Result(true,2000,"评论获取成功",comments);
        }catch (Exception e){
            e.printStackTrace();
            logService.addLog("评论获取",e.getMessage(),"",-1);
            return new Result(false,4000,"评论获取失败");
        }
    }


    @PostMapping("/article/comment")
    public Result articleComment(
            HttpServletRequest request,
            @RequestBody Map<String,Object> map
    ){
        Claims user_claims = (Claims)request.getAttribute("user_claims");
        if (user_claims==null){
            return new Result(false, 4000,"未登录");
        }

        try {
            String content = (String)map.get("content");
            Integer cid = (Integer)map.get("cid");
            Integer authorId = (Integer)map.get("authorId");
            Integer ownerId = (Integer)map.get("ownerId");
            if (cid==null||authorId==null||ownerId==null||content==null){
                return new Result(false,4000,"评论添加失败");
            }
            CommentDomain commentDomain = new CommentDomain();
            commentDomain.setContent(content);
            commentDomain.setCid(cid);
            commentDomain.setAuthorId(authorId);
            commentDomain.setOwnerId(ownerId);
            commentService.addComment(commentDomain);
            return new Result(true,2000,"评论添加成功");
        }catch (Exception e){
            e.printStackTrace();
            logService.addLog("评论添加",e.getMessage(),"",-1);
            return new Result(false,4000,"评论添加失败");
        }
    }

    @GetMapping("/article/user")
    public Result articleUser(
            @RequestParam(name = "uid", required = true)
                    Integer uid
    ){
        try {
            UserDomain user = userService.getUserInfoById(uid);
            if (user==null){
                return new Result(false,4000,"用户信息失败,null");
            }
            user.setPassword("null");
            return new Result(true,2000,"用户信息成功",user);
        }catch (Exception e){
            e.printStackTrace();
            logService.addLog("获取用户信息",e.getMessage(),"",-1);
            return new Result(false,4000,"用户信息失败");
        }
    }

    @GetMapping("/article/latest")
    public Result latestArticle(
            @RequestParam(name = "pageNum", required = true, defaultValue = "1")
            Integer pageNum,
            @RequestParam(name = "pageSize", required = true, defaultValue = "8")
            Integer pageSize
    ){

        try {
            PageInfo<ContentDomain> articles = contentService.getRecentlyArticle(pageNum, pageSize);
            if (articles==null){
                return new Result(false,4000,"最新文章失败,null");
            }
            return new Result(true,2000,"最新文章成功",articles);
        }catch (Exception e){
            e.printStackTrace();
            logService.addLog("获取最新文章",e.getMessage(),"",-1);
            return new Result(false,4000,"最新文章失败");
        }
    }

    @GetMapping("/article/search")
    public Result searchArticle(
            @RequestParam(name = "pageNum", required = true, defaultValue = "1")
                    Integer pageNum,
            @RequestParam(name = "pageSize", required = true, defaultValue = "8")
                    Integer pageSize,
            @RequestParam(name = "title", required = true)
                    String title,
            @RequestParam(name = "tag", required = false)
                    String tag,
            @RequestParam(name = "category", required = false)
                    String category
    ){

        try {
            ContentCond contentCond = new ContentCond();
            contentCond.setTitle(title);
            contentCond.setTag(tag);
            contentCond.setCategory(category);
            PageInfo<ContentDomain> articles = contentService.getArticlesByCond(contentCond,pageNum, pageSize);
            if (articles==null){
                return new Result(false,4000,"找不到文章");
            }
            return new Result(true,2000,"搜索到文章",articles);
        }catch (Exception e){
            e.printStackTrace();
            logService.addLog("搜索文章",e.getMessage(),"",-1);
            return new Result(false,4000,"搜索文章失败");
        }
    }


}
