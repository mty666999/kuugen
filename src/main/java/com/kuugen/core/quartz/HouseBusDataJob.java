package com.kuugen.core.quartz;

import cn.stylefeng.roses.core.util.ToolUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kuugen.core.beetl.SpringContextUtil;
import com.kuugen.core.config.UrlConfig;
import com.kuugen.core.log.LogManager;
import com.kuugen.core.log.factory.LogTaskFactory;
import com.kuugen.core.shiro.ShiroKit;
import com.kuugen.core.util.DateUtil;
import com.kuugen.core.util.StringUtil;
import com.kuugen.modular.house.data.model.HouseBusDataModel;
import com.kuugen.modular.house.data.service.HouseBusDataService;
import com.kuugen.modular.toolmgr.util.WeatherUtil;
import com.kuugen.modular.toolmgr.weather.model.WeatherModel;
import com.kuugen.modular.toolmgr.weather.service.WeatherService;
import net.dongliu.requests.Requests;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author: mty
 * @Description:房屋交易数据获取
 * @Date: Created in 22:29 2019-05-03
 * @Modified By:
 */
@DisallowConcurrentExecution
public class HouseBusDataJob implements Job {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            HouseBusDataService houseBusDataService = SpringContextUtil.getBean("houseBusDataService");
            //能获取到的数据只有福州市的
            //福州id 2
            Long cityId=2l;
            String houseUrl=UrlConfig.FZhouseDataUrl+0;
            Connection con = Jsoup.connect(houseUrl);
            //解析请求结果
            Document doc=null;
            try {
                doc = con.ignoreContentType(true).get();
                Elements trs= doc.body().select("tr[height]") ;
                //解析tr
                for(int i=0;i<trs.size();i++){
                    Element tr=trs.get(i);
                    Elements a=tr.select("a");
                    String href=a.get(0).attr("href");//直接取属性
                    String title=a.get(0).attr("title");//直接取属性
                    String dateStr=title.split("福州住宅签约")[0];
                    Date date=DateUtil.parseDate(dateStr.replace("年","-").replace("月","-").replace("日",""));
                    System.out.println(DateUtil.format(date));
                    String[] data=title.split("福州住宅签约")[1].split("面积");
                    //data1抽取数字作为成交套数
                    int chengjiao = StringUtil.extractIntForStr(data[0]);
                    double totalArea=StringUtil.extractDoubleForStr(data[1]);
                    HouseBusDataModel house=new HouseBusDataModel();
                    house.setCityId(cityId);
                    house.setDate(date);
                    house.setUrl(href);
                    house.setZhuzhai(chengjiao);
                    house.setTotalArea(totalArea);
                    house.setUpdateTime(new Date());
                    QueryWrapper<HouseBusDataModel> wrapper = new QueryWrapper<HouseBusDataModel>();
                    wrapper.eq("city_id",cityId);
                    wrapper.eq("date",date);
                    int count=houseBusDataService.count(wrapper);
                    if(count==0){
                        houseBusDataService.save(house);
                    }else{

                    }

                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }catch (Exception e){
            LogManager.me().executeLog(LogTaskFactory.exceptionLog(ShiroKit.getUser().getId(),e));
        }
    }


}
