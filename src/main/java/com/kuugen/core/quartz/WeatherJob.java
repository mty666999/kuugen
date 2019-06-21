package com.kuugen.core.quartz;

import cn.stylefeng.roses.core.util.ToolUtil;
import com.alibaba.fastjson.JSONObject;
import com.kuugen.core.beetl.SpringContextUtil;
import com.kuugen.core.config.UrlConfig;
import com.kuugen.core.log.LogManager;
import com.kuugen.core.log.factory.LogTaskFactory;
import com.kuugen.core.shiro.ShiroKit;
import com.kuugen.modular.toolmgr.util.WeatherUtil;
import com.kuugen.modular.toolmgr.weather.model.WeatherModel;
import com.kuugen.modular.toolmgr.weather.service.WeatherService;
import net.dongliu.requests.Requests;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author: mty
 * @Description:
 * @Date: Created in 22:29 2019-05-03
 * @Modified By:
 */
@DisallowConcurrentExecution
public class WeatherJob implements Job {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            WeatherService weatherService = SpringContextUtil.getBean("weatherService");
            //首先获取所有用户关注的城市
            List<Long> citys = weatherService.getUserCitys();
            if (0 == citys.size()) {
                log.debug("没有关注城市,不进行获取天气信息");
            } else {
                //循环获取天气信息
                for (int i = 0; i < citys.size(); i++) {
                    String weatherUrl = UrlConfig.weatherUrl + citys.get(i).toString();
                    //解析天气
                    JSONObject json = Requests.get(weatherUrl).send().readToJson(JSONObject.class);
                    Map map = WeatherUtil.getWeatherMapByJson(json);
                    for (Iterator j = map.keySet().iterator(); j.hasNext(); ) {
                        Object obj = j.next();
                        /**
                         * 首先处理当前天气(未来)
                         * 1.根据城市+日期判断有无数据(没有-新增结束;有-更新)
                         * 懒人处理方式-_-
                         */
                        WeatherModel weatherInfo = (WeatherModel) map.get(obj);
                        String weatherId = weatherService.getWeatherId(weatherInfo);
                        if (ToolUtil.isEmpty(weatherId)) {
                            weatherInfo.setUpdate_time(new Date());
                            weatherService.save(weatherInfo);
                        } else {
                            weatherInfo.setWeatherId(Long.parseLong(weatherId));
                            weatherInfo.setUpdate_time(new Date());
                            weatherService.updateById(weatherInfo);
                        }
                    }


                }

            }
        }catch (Exception e){
            e.printStackTrace();
            LogManager.me().executeLog(LogTaskFactory.exceptionLog(1l,e));
        }
    }


}
