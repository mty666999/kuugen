package com.kuugen.modular.toolmgr.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kuugen.core.util.DateUtil;
import com.kuugen.modular.toolmgr.weather.model.WeatherModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: mty
 * @Description:天气工具类,方便解析数据
 * @Date: Created in 12:25 2019-05-04
 * @Modified By:
 */
public class WeatherUtil {

    public static Map getWeatherMapByJson(JSONObject json){
        Map map= new HashMap();
        //获取城市信息
        JSONObject city=json.getJSONObject("cityInfo");
        JSONObject data= json.getJSONObject("data");
        JSONObject yesterday =json.getJSONObject("data").getJSONObject("yesterday");
        JSONArray forecast = json.getJSONObject("data").getJSONArray("forecast");
        WeatherModel nowWeather =new WeatherModel();
        nowWeather.setCityId(Long.parseLong(city.get("cityId").toString()));
        nowWeather.setWeatherDate( DateUtil.parseDate(   ((Map)forecast.get(0)).get("ymd").toString()));
        nowWeather.setDataTime(  city.get("updateTime").toString() );
        nowWeather.setStatue("1");
        nowWeather.setCityName( city.getString("city"));
        nowWeather.setPcityName(city.getString("parent"));
        nowWeather.setShidu(data.getString("shidu"));
        nowWeather.setPm25(data.getString("pm25"));
        nowWeather.setQuality(data.getString("quality"));
        nowWeather.setWendu(data.getString("wendu"));
        nowWeather.setHigh(((JSONObject)forecast.get(0)).getString("high") );
        nowWeather.setLow(((JSONObject)forecast.get(0)).getString("low") );
        nowWeather.setFl(((JSONObject)forecast.get(0)).getString("fl"));
        nowWeather.setFx(((JSONObject)forecast.get(0)).getString("fx"));
        nowWeather.setType(((JSONObject)forecast.get(0)).getString("type"));
        nowWeather.setNotice(((JSONObject)forecast.get(0)).getString("notice"));
        map.put("nowWeather",nowWeather);
        //然后初始化昨天天气
        WeatherModel yestday =new WeatherModel();
        yestday.setCityId(Long.parseLong(city.get("cityId").toString()));
        yestday.setWeatherDate( DateUtil.parseDate(   yesterday.get("ymd").toString()));
        yestday.setDataTime(  city.get("updateTime").toString() );
        yestday.setStatue("0");
        yestday.setCityName( city.getString("city"));
        yestday.setPcityName(city.getString("parent"));
        yestday.setHigh(yesterday.getString("high") );
        yestday.setLow(yesterday.getString("low") );
        yestday.setFl(yesterday.getString("fl"));
        yestday.setFx(yesterday.getString("fx"));
        yestday.setType(yesterday.getString("type"));
        yestday.setNotice(yesterday.getString("notice"));
        map.put("yestday",yestday);
        //然后获取后面两天的数据
        WeatherModel tomorrow=new WeatherModel();
        tomorrow.setCityId(Long.parseLong(city.get("cityId").toString()));
        tomorrow.setWeatherDate( DateUtil.parseDate(   ((Map)forecast.get(1)).get("ymd").toString()));
        tomorrow.setStatue("2");
        tomorrow.setCityName( city.getString("city"));
        tomorrow.setPcityName(city.getString("parent"));
        tomorrow.setDataTime(  city.get("updateTime").toString() );
        tomorrow.setHigh(((JSONObject)forecast.get(1)).getString("high") );
        tomorrow.setLow(((JSONObject)forecast.get(1)).getString("low") );
        tomorrow.setFl(((JSONObject)forecast.get(1)).getString("fl"));
        tomorrow.setFx(((JSONObject)forecast.get(1)).getString("fx"));
        tomorrow.setType(((JSONObject)forecast.get(1)).getString("type"));
        tomorrow.setNotice(((JSONObject)forecast.get(1)).getString("notice"));
        map.put("tomorrow",tomorrow);

        WeatherModel afterTomorrow=new WeatherModel();
        afterTomorrow.setCityId(Long.parseLong(city.get("cityId").toString()));
        afterTomorrow.setWeatherDate( DateUtil.parseDate(   ((Map)forecast.get(2)).get("ymd").toString()));
        afterTomorrow.setStatue("2");
        afterTomorrow.setCityName( city.getString("city"));
        afterTomorrow.setPcityName(city.getString("parent"));
        afterTomorrow.setDataTime(  city.get("updateTime").toString() );
        afterTomorrow.setHigh(((JSONObject)forecast.get(2)).getString("high") );
        afterTomorrow.setLow(((JSONObject)forecast.get(2)).getString("low") );
        afterTomorrow.setFl(((JSONObject)forecast.get(2)).getString("fl"));
        afterTomorrow.setFx(((JSONObject)forecast.get(2)).getString("fx"));
        afterTomorrow.setType(((JSONObject)forecast.get(2)).getString("type"));
        afterTomorrow.setNotice(((JSONObject)forecast.get(2)).getString("notice"));
        map.put("afterTomorrow",afterTomorrow);
        return map;
    }
}
