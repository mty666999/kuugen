package com.kuugen.stock;

import com.kuugen.KuugenApplication;
import com.kuugen.core.util.DateUtils;
import com.kuugen.core.util.JsonUtil;
import com.kuugen.modular.stock.model.TbStockMainModel;
import com.kuugen.modular.stock.stockinfo.mapper.StockMainMapper;
import com.kuugen.modular.stock.stockinfo.service.StockMainService;
import com.kuugen.modular.stock.thread.StockContrastWork;
import com.kuugen.modular.toolmgr.weather.service.WeatherService;
import com.kuugen.modular.work.thread.StockHisDataWork;
import com.kuugen.modular.worm.service.WormService;
import com.kuugen.modular.worm.util.html.SinaStockUtil;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import javax.sound.midi.Soundbank;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KuugenApplication.class)
@WebAppConfiguration
public class StockTest {
    @Resource
    WeatherService weatherService;
    @Resource
    StockMainService stockMainService;
    @Resource
    WormService wormService;
    @Resource
    StockMainMapper stockMainMapper;
    @Test
    public  void initStock(){
        List<Map<String,String>> stocklist =stockMainService.getStockList(); //new ArrayList<>();//
//        TbStockMainModel s1=new TbStockMainModel();
//        TbStockMainModel s2=new TbStockMainModel();
//        s1.setStock_code("300059");
//        s1.setStock_type("sz");
//        s2.setStock_code("300377");
//        s2.setStock_type("sz");
//        stocklist.add(s1);
//        stocklist.add(s2);
        for(Map<String,String> stock:stocklist){
            System.out.println(JsonUtil.bean2json(stock));
        }


        //获取当前年月
        int year =DateUtils.getNowYear();
        int jidu =DateUtils.getNowJidu();
        ExecutorService exec = Executors.newCachedThreadPool();
        //遍历股票然后分季度获取数据
        for(int i=0;i<stocklist.size();i++){
//		for(int i=0;i<1;i++){
            String stock_code=stocklist.get(i).get("stock_code");
//			String stock_code="300003";
            //获取3年中的数据,年份遍历 j=年份
            for(int j=year -4;j<=year;j++ ){
                if(year==j){
                    //今年度的就以当前季度作为最大,季度遍历 n=季度
                    for(int n=1;n<=jidu;n++){
                        StockHisDataWork work=new StockHisDataWork( stock_code, j, n);
                        exec.submit(work);
                    }
                }else{
                    //往年度的就1-4,季度遍历 n=季度
                    for(int n=1;n<=4;n++){
                        StockHisDataWork work=new StockHisDataWork( stock_code, j, n);
                        exec.submit(work);
                    }
                }
            }
        }

        exec.shutdown();
        try {
            exec.awaitTermination(1, TimeUnit.HOURS);
            System.out.println("结束");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("超时");
        }
    }
    @Test
    public  void testStock(){
        //模拟历史数据回测
        //先获取股票列表
        List<TbStockMainModel> stockList = new ArrayList<>();//stockMainService.getStockList();
        TbStockMainModel s1=new TbStockMainModel();
        TbStockMainModel s2=new TbStockMainModel();
        s1.setStock_code("300059");
        s1.setStock_type("sz");
        s2.setStock_code("300377");
        s2.setStock_type("sz");
        stockList.add(s1);
        stockList.add(s2);
        StringBuilder url=new StringBuilder();
        StringBuilder sb=new StringBuilder();
        try {
            for(int i=0;i<stockList.size();i++){
                sb.append(stockList.get(i).getStock_type());
                sb.append(stockList.get(i).getStock_code() );
                sb.append(",");
            }
            url.append("http://hq.sinajs.cn/list=");
            url.append( sb);
            //首先获取返回doc内容
            Document doc=wormService.getHtmlForUrl(url.toString(),0);
            ExecutorService exec = Executors.newCachedThreadPool();
            if(doc != null){
                List<Map<String, String>> nowDatas= SinaStockUtil.getNowStockData(doc);
                for(Map nowData :nowDatas){
                    StockContrastWork work=new StockContrastWork( nowData);
                    exec.submit(work);
                }
                exec.shutdown();

                exec.awaitTermination(1, TimeUnit.HOURS);
                System.out.println("结束");

            }else{

            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("超时");

        }catch (Exception e) {

        }
    }

}
