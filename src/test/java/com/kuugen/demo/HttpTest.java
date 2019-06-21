package com.kuugen.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kuugen.KuugenApplication;
import com.kuugen.core.util.DateUtil;
import com.kuugen.modular.system.model.SysQuartz;
import com.kuugen.modular.system.service.DictService;
import com.kuugen.modular.toolmgr.weather.model.WeatherModel;
import com.kuugen.modular.toolmgr.weather.service.WeatherService;
import net.dongliu.requests.Requests;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: mty
 * @Description:网络测试
 * @Date: Created in 14:19 2019-04-21
 * @Modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KuugenApplication.class)
@WebAppConfiguration
public class HttpTest {
    @Resource
    WeatherService  weatherService;
    @Test
    public   void test(){
//		获取到可用代理IP	118.190.95.43	9001
//		获取到可用代理IP	61.135.217.7	80
        //测试天气接口 城市代码 {101230101, "福州"},{101230103, "闽侯"},
        // {101200101, "武汉"},{101180101, "郑州"},{101180405, "禹州"},
        String cityCode="101230103";
        String weatherUrl="http://t.weather.sojson.com/api/weather/city/"+cityCode;
        //String resp = Requests.get(weatherUrl).send().readToText();
        //解析天气
        JSONObject json=Requests.get(weatherUrl).send().readToJson(JSONObject.class);
        //获取当天数据
        System.out.println(json.getJSONObject("data").getJSONArray("forecast").get(0));
        //先判断有没有存在数据,不存在新增,存在更新
        {
            //获取城市信息
            JSONObject city=json.getJSONObject("cityInfo");
            JSONObject data= json.getJSONObject("data");
            JSONObject yesterday =json.getJSONObject("data").getJSONObject("yesterday");
            JSONArray forecast = json.getJSONObject("data").getJSONArray("forecast");
            WeatherModel weatherModel=new WeatherModel();
            weatherModel.setCityId(Long.parseLong(city.get("cityId").toString()));
            weatherModel.setWeatherDate( DateUtil.parseDate(   ((Map)forecast.get(0)).get("ymd").toString()));
            weatherModel.setDataTime(  city.get("updateTime").toString() );
            weatherModel.setStatue("1");
            weatherModel.setCityName( city.getString("city"));
            weatherModel.setPcityName(city.getString("parent"));
            weatherModel.setShidu(data.getString("shidu"));
            weatherModel.setPm25(data.getString("pm25"));
            weatherModel.setQuality(data.getString("quality"));
            weatherModel.setHigh(((JSONObject)forecast.get(0)).getString("high") );
            weatherModel.setLow(((JSONObject)forecast.get(0)).getString("low") );
            weatherModel.setFl(((JSONObject)forecast.get(0)).getString("fl"));
            weatherModel.setFx(((JSONObject)forecast.get(0)).getString("fx"));
            weatherModel.setType(((JSONObject)forecast.get(0)).getString("type"));
            weatherModel.setNotice(((JSONObject)forecast.get(0)).getString("notice"));
            weatherModel.setUpdate_time(new Date());
            Map map=new HashMap();
            map.put("weather",weatherModel);
            QueryWrapper<WeatherModel> wrapper = new QueryWrapper<WeatherModel>();
            wrapper.eq("city_id",Long.parseLong(city.get("cityId").toString()));
            wrapper.eq("weather_date",DateUtil.parseDate(   ((Map)forecast.get(0)).get("ymd").toString()));
            int count=weatherService.count(wrapper);
            System.out.println(count);
            if(0==count){
                weatherService.save(weatherModel);
            }else{

            }
        }
        //获取昨天数据
        //System.out.println(json.getJSONObject("data").get("yesterday").toString());
        //System.out.println(( (JSONArray) json.get("data")).get(0).toString());
        //获取请求连接
        //Connection con = Jsoup.connect("http://hq.sinajs.cn/list=sz002555").proxy("118.190.95.43", 9001);

        //Connection con = Jsoup.connect(weatherUrl);


        //解析请求结果
//        Document doc=null;
//        try {
//            doc = con.ignoreContentType(true).get();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        //处理获取到的数据

//		Document doc=Jsoup.connect("http://hq.sinajs.cn/list=sz002555")
//	            .timeout(3*1000)
//	            .proxy("122.114.31.177", 808)
//	            .get();
        //System.out.println(doc.text());
    }

    public static InetAddress getInetAddress(){

        try{
            return InetAddress.getLocalHost();
        }catch(UnknownHostException e){
            System.out.println("unknown host!");
        }
        return null;

    }
    public static String getHostName(InetAddress netAddress){
        if(null == netAddress){
            return null;
        }
        String name = netAddress.getHostName(); //get the host address
        return name;
    }
    public static String getHostIp(InetAddress netAddress){
        if(null == netAddress){
            return null;
        }
        String ip = netAddress.getHostAddress(); //get the ip address
        return ip;
    }
}
