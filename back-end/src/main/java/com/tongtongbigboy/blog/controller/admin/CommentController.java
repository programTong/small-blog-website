package com.tongtongbigboy.blog.controller.admin;

import com.github.pagehelper.PageInfo;
import com.tongtongbigboy.blog.constant.ErrorConstant;
import com.tongtongbigboy.blog.dto.Result;
import com.tongtongbigboy.blog.dto.cond.CommentCond;
import com.tongtongbigboy.blog.model.CommentDomain;
import com.tongtongbigboy.blog.service.comment.CommentService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 评论相关接口
 *
 */

@RestController("adminCommentController")
@RequestMapping("/admin/comments")
@CrossOrigin
public class CommentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);


    @Autowired
    private CommentService commentService;

    @GetMapping(value = "")
    public Result index(
            @ApiParam(name = "page", value = "页数", required = false)
            @RequestParam(name = "page", required = false, defaultValue = "1")
            Integer page,
            @ApiParam(name = "page_size", value = "每页条数", required = false)
            @RequestParam(name = "page_size", required = false, defaultValue = "15")
            Integer page_size,
            HttpServletRequest request
    ){
        Claims user_claims = (Claims)request.getAttribute("admin_claims");
        if (user_claims==null){
            return new Result(false, 4000,"未登录");
        }
        PageInfo<CommentDomain> comments = commentService.getCommentsByCond(new CommentCond(), page, page_size);

        return new Result(true, 2000, "进入评论列表页",comments);
    }


    @PostMapping(value = "/delete")
    public Result deleteComment(
            @RequestBody Map<String,Object> map,
            HttpServletRequest request
    ){
        Claims user_claims = (Claims)request.getAttribute("admin_claims");
        if (user_claims==null){
            return new Result(false, 4000,"未登录");
        }

        Integer coid = (Integer)map.get("coid");
        try {
            CommentDomain comment = commentService.getCommentById(coid);
            if (null == comment)
                throw new RuntimeException(ErrorConstant.Comment.COMMENT_NOT_EXIST);

            commentService.deleteComment(coid);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return new Result(false, 4000, e.getMessage());
        }
        return new Result(true, 2000, "删除一条评论");
    }



}
