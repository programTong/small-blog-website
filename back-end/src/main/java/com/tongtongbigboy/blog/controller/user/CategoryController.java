package com.tongtongbigboy.blog.controller.user;

import com.tongtongbigboy.blog.constant.Types;
import com.tongtongbigboy.blog.constant.WebConst;
import com.tongtongbigboy.blog.dto.MetaDto;
import com.tongtongbigboy.blog.dto.Result;
import com.tongtongbigboy.blog.model.MetaDomain;
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
@RestController
@RequestMapping("user/category")
@CrossOrigin
public class CategoryController  {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private MetaService metaService;


    @GetMapping(value = "")
    public Result index(HttpServletRequest request){
        Claims user_claims = (Claims)request.getAttribute("user_claims");
        if (user_claims==null){
            return new Result(false, 4000,"未登录");
        }

        List<MetaDto> categories = metaService.getMetaList((Integer) user_claims.get("uid"),Types.CATEGORY.getType(), null, WebConst.MAX_POSTS);
        List<MetaDto> tags = metaService.getMetaList((Integer) user_claims.get("uid"),Types.TAG.getType(), null, WebConst.MAX_POSTS);
        Map<String,Object> map = new HashMap<>();
        map.put("categories",categories);
        map.put("tags",tags);
        return new Result(true, 2000, "进入分类和标签页成功",map);
    }


    @PostMapping
    public Result addCategory(
            HttpServletRequest request,
            @RequestBody Map<String,Object> map
    ){
        Claims user_claims = (Claims)request.getAttribute("user_claims");
        if (user_claims==null){
            return new Result(false, 4000,"未登录");
        }

        String cname = (String)map.get("cname");
        if (cname==null||cname.length()==0){
            return new Result(false, 4000, "分类保存失败");
        }

        MetaDomain metaDomain = new MetaDomain();
        metaDomain.setName(cname);
        metaDomain.setType("category");
        metaDomain.setSlug(cname);
        metaDomain.setUid((Integer) user_claims.get("uid"));
        try {
            metaService.addMeta(metaDomain);
        } catch (Exception e) {
            e.printStackTrace();
            String msg = "分类保存失败";
            LOGGER.error(msg, e);

            return new Result(false, 4000, msg);
        }
        return new Result(true, 2000, "保存分类成功");
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
        Claims user_claims = (Claims)request.getAttribute("user_claims");
        if (user_claims==null){
            return new Result(false, 4000,"未登录");
        }

        Integer mid = (Integer)map.get("mid");
        if (mid==null){
            return new Result(false, 4000, "删除分类失败");
        }
        try {
            metaService.deleteMetaById(mid,(Integer) user_claims.get("uid"));

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return new Result(false, 4000, e.toString());
        }
        return new Result(true, 2000, "删除分类");
    }
}
