package com.kuugen.modular.work.thread;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.kuugen.modular.stock.dayk.service.StockDaykService;
import com.kuugen.modular.stock.stockinfo.service.StockMainService;
import com.kuugen.modular.stock.stockinfo.service.impl.StockMainServiceImpl;
import com.kuugen.modular.worm.service.WormService;
import com.kuugen.modular.worm.util.html.SinaStockUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 获取股票历史数据的多线程实现类
 * @author mty
 *
 */
@Component 
public class StockHisDataWork implements Runnable{

	private static final Log log = LogFactory.getLog(StockHisDataWork.class);
	@Autowired
	private StockDaykService stockDaykService;
	@Autowired
	private StockMainService stockMainService;
	@Autowired
	private WormService wormService;
	private static StockHisDataWork stockHisDataWork;  
	  
	public void setStockDaykService(StockDaykService  stockDaykService) {  
        this.stockDaykService = stockDaykService;  
    } 
	public void setStockMainService(StockMainService  stockMainService) {  
        this.stockMainService = stockMainService;  
    } 
	public void setWormService(WormService  wormService) {  
        this.wormService = wormService;  
    } 
	      
	    @PostConstruct  
	    public void init() {  
	    	stockHisDataWork = this;  
	    	stockHisDataWork.stockDaykService = this.stockDaykService;  
	    	stockHisDataWork.wormService = this.wormService;  
	    	stockHisDataWork.stockMainService = this.stockMainService;  
	    } 
	
	//private CountDownLatch threadsSignal;
	private String stock_code;//股票代码
	private int year;//年份
	private int jidu;//季度
	public StockHisDataWork(){
		
	}
	public StockHisDataWork(String stock_code,int year,int jidu ) {
		//this.threadsSignal = threadsSignal;
		this.stock_code=stock_code;
		this.year = year;
		this.jidu =jidu;
	}
	//获取股票历史数据并更新他们
	@Override
	public void run() {
		log.warn(Thread.currentThread().getName()+" "+ stock_code+" "+year+" "+jidu+"  : "+getTest());
		// TODO Auto-generated method stub
		StringBuffer url =new StringBuffer();
		url.append("http://money.finance.sina.com.cn/corp/go.php/vMS_MarketHistory/stockid/");
		url.append(stock_code).append(".phtml?");
		url.append("year="+year);
		url.append("&jidu="+jidu);
		//首先获取返回doc内容
		Document doc = stockHisDataWork.wormService.getHtmlForUrl(url.toString(),0);
		if(doc != null){
			List<Map<String, String>> stocklist= SinaStockUtil.getHistotyStockData(doc);
			log.debug("调用开始");
			stockHisDataWork.stockDaykService.saveHisData(stocklist,stock_code,year,jidu);
		}else{
			log.warn(stock_code+" : "+year+"年"+jidu+"获取数据失败");
		}
	}
	public int getTest(){
		return year*jidu;
	}
}
