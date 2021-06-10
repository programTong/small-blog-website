package com.tongtongbigboy.blog.service.option.impl;

import com.tongtongbigboy.blog.constant.ErrorConstant;
import com.tongtongbigboy.blog.dao.OptionDao;
import com.tongtongbigboy.blog.model.OptionsDomain;
import com.tongtongbigboy.blog.service.option.OptionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 网站配置服务层
 * Created by Donghua.Chen on 2018/4/28.
 */
@Service
public class OptionServiceImpl implements OptionService {

    @Autowired
    private OptionDao optionDao;

    @Override

    public void deleteOptionByName(String name) {
        if(StringUtils.isBlank(name))
            throw new RuntimeException(ErrorConstant.Common.PARAM_IS_EMPTY);
        optionDao.deleteOptionByName(name);

    }

    @Override
    @Transactional

    public void updateOptionByName(String name, String value) {
        if(StringUtils.isBlank(name))
            throw new RuntimeException(ErrorConstant.Common.PARAM_IS_EMPTY);
        OptionsDomain option = new OptionsDomain();
        option.setName(name);
        option.setValue(value);
        optionDao.updateOptionByName(option);

    }

    @Override
    @Transactional

    public void saveOptions(Map<String, String> options) {
        if (null != options && !options.isEmpty()) {
            options.forEach(this::updateOptionByName);
        }
    }

    @Override

    public OptionsDomain getOptionByName(String name) {
        if(StringUtils.isBlank(name))
            throw new RuntimeException(ErrorConstant.Common.PARAM_IS_EMPTY);
        return optionDao.getOptionByName(name);
    }

    @Override

    public List<OptionsDomain> getOptions() {
        return optionDao.getOptions();
    }
}
