package com.kuugen.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.kuugen.KuugenApplication;
import com.kuugen.core.config.UrlConfig;
import com.kuugen.core.util.DateUtil;
import com.kuugen.core.util.StringUtil;
import com.kuugen.modular.house.data.model.HouseBusDataModel;
import com.kuugen.modular.house.data.service.HouseBusDataService;
import com.kuugen.modular.toolmgr.weather.model.WeatherModel;
import com.kuugen.modular.toolmgr.weather.service.WeatherService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * @Author: mty
 * @Description: 房产数据获取
 * @Date: Created in 16:06 2019-05-08
 * @Modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KuugenApplication.class)
@WebAppConfiguration
public class HouseTest {
    @Resource
    HouseBusDataService  houseBusDataService;

    @Test
    public void test(){
        //福州id 2
        Long cityId=2l;
        for(int page=1;page<20;page++){
            String houseUrl=UrlConfig.FZhouseDataUrl+page;
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
                Thread.sleep(10000l);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args){
        String page="10";
        String houseUrl=UrlConfig.FZhouseDataUrl+page;
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
                System.out.println(href+"  "+dateStr+" "+chengjiao+" "+totalArea);

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
