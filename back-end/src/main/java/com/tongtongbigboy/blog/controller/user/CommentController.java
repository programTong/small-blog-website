package com.tongtongbigboy.blog.controller.user;

import com.github.pagehelper.PageInfo;
import com.tongtongbigboy.blog.constant.ErrorConstant;
import com.tongtongbigboy.blog.dto.cond.CommentCond;
import com.tongtongbigboy.blog.model.CommentDomain;
import com.tongtongbigboy.blog.dto.Result;
import com.tongtongbigboy.blog.service.comment.CommentService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 评论相关接口
 *
 */

@RestController
@RequestMapping("/user/comments")
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

        PageInfo<CommentDomain> comments = commentService.getCommentsByCond(new CommentCond(), page, page_size);
        request.setAttribute("comments", comments);
        return new Result(true, 2000, "进入评论列表页");
    }


    @PostMapping(value = "/delete")
    public Result deleteComment(
            @ApiParam(name = "coid", value = "评论编号", required = true)
            @RequestParam(name = "coid", required = true)
            Integer coid
    ){

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


    @PostMapping(value = "/status")
    public Result changeStatus(
            @ApiParam(name = "coid", value = "评论主键", required = true)
            @RequestParam(name = "coid", required = true)
            Integer coid,
            @ApiParam(name = "status", value = "状态", required = true)
            @RequestParam(name = "status", required = true)
            String status
    ){
        try {
            CommentDomain comment = commentService.getCommentById(coid);
            if (null != comment){
                commentService.updateCommentStatus(coid, status);
            }else{
                return new Result(false, 4000, "删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return new Result(false, 4000, e.getMessage());
        }
        return new Result(true, 2000, "更改评论状态");
    }



}
