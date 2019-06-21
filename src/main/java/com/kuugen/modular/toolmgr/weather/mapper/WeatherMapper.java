package com.kuugen.modular.toolmgr.weather.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kuugen.modular.toolmgr.weather.model.WeatherModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 天气 Mapper 接口
 * </p>
 *
 * @author mty
 * @since
 */
public interface WeatherMapper extends BaseMapper<WeatherModel> {


    List<Long> getUserCitys();

    String getWeatherId(WeatherModel nowWeather);
}
