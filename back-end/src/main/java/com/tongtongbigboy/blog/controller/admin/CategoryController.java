package com.tongtongbigboy.blog.controller.admin;

import com.tongtongbigboy.blog.constant.Types;
import com.tongtongbigboy.blog.constant.WebConst;
import com.tongtongbigboy.blog.dto.MetaDto;
import com.tongtongbigboy.blog.dto.Result;
import com.tongtongbigboy.blog.service.meta.MetaService;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分类和标签
 */
@RestController("adminCategoryController")
@RequestMapping("/admin/category")
@CrossOrigin
public class CategoryController  {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private MetaService metaService;


    @GetMapping(value = "")
    public Result index(HttpServletRequest request){
        Claims user_claims = (Claims)request.getAttribute("admin_claims");
        if (user_claims==null){
            return new Result(false, 4000,"未登录");
        }

        List<MetaDto> categories = metaService.getMetaList(null,Types.CATEGORY.getType(), null, WebConst.MAX_POSTS);
        List<MetaDto> tags = metaService.getMetaList(null,Types.TAG.getType(), null, WebConst.MAX_POSTS);
        Map<String,Object> map = new HashMap<>();
        map.put("categories",categories);
        map.put("tags",tags);
        return new Result(true, 2000, "进入分类和标签页成功",map);
    }



    /**
     * 删除分类
     * @param
     * @return
     */
    @PostMapping("delete")
    public Result delete(
            HttpServletRequest request,
            @RequestBody Map<String,Object> map
    ){
        Claims user_claims = (Claims)request.getAttribute("admin_claims");
        if (user_claims==null){
            return new Result(false, 4000,"未登录");
        }

        Integer mid = (Integer)map.get("mid");
        Integer uid = (Integer)map.get("uid");
        if (mid==null){
            return new Result(false, 4000, "删除分类失败");
        }
        try {
            metaService.deleteMetaById(mid,uid);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return new Result(false, 4000, e.toString());
        }
        return new Result(true, 2000, "删除分类");
    }
}
