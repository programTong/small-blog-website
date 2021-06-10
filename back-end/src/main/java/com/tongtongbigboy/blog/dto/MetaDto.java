package com.tongtongbigboy.blog.dto;

import com.tongtongbigboy.blog.model.MetaDomain;

/**
 * 标签、分类列表
 *
 */
public class MetaDto extends MetaDomain {

    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
