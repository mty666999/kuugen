package com.kuugen.modular.toolmgr.weather.service;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuugen.core.common.exception.BizExceptionEnum;
import com.kuugen.core.common.page.LayuiPageFactory;
import com.kuugen.modular.system.mapper.DictMapper;
import com.kuugen.modular.system.model.DictDto;
import com.kuugen.modular.toolmgr.weather.mapper.WeatherMapper;
import com.kuugen.modular.toolmgr.weather.model.WeatherModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Service
public class WeatherService extends ServiceImpl<WeatherMapper, WeatherModel> {

    @Resource
    private WeatherMapper weatherMapper;

    /**
     * 获取所有关注的城市列表
     *
     */
    public List<Long> getUserCitys() {
        return  weatherMapper.getUserCitys();
    }

    /**
     * 根据城市+时间获取id
     * @param nowWeather
     * @return
     */
    public String getWeatherId(WeatherModel nowWeather) {
        return  weatherMapper.getWeatherId(nowWeather);
    }
}
