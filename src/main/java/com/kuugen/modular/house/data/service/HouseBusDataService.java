package com.kuugen.modular.house.data.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuugen.modular.house.data.mapper.HouseBusDataMapper;
import com.kuugen.modular.house.data.model.HouseBusDataModel;
import com.kuugen.modular.toolmgr.weather.mapper.WeatherMapper;
import com.kuugen.modular.toolmgr.weather.model.WeatherModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Service
public class HouseBusDataService extends ServiceImpl<HouseBusDataMapper, HouseBusDataModel> {

    @Resource
    private HouseBusDataMapper houseBusDataMapper;


}
