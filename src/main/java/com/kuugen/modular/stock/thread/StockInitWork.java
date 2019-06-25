package com.kuugen.modular.stock.thread;

import javax.annotation.PostConstruct;


import com.kuugen.modular.stock.dayk.service.StockDaykService;
import com.kuugen.modular.stock.stockinfo.service.StockMainService;
import com.kuugen.modular.worm.service.WormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 股票初始化的多线程实现类
 * @author mty
 *
 */
@Component 
public class StockInitWork implements Runnable{
	
	private static final Logger logger = LoggerFactory.getLogger(StockInitWork.class);
	@Autowired
	private StockDaykService stockDaykService;
	@Autowired
	private StockMainService stockMainService;
	@Autowired
	private WormService wormService;
	private static StockInitWork stockInitWork;  
	  
	public void setStockDaykService(StockDaykService  stockDaykService) {  
        this.stockDaykService = stockDaykService;  
    } 
	public void setStockMainService(StockMainService  stockMainService) {  
        this.stockMainService = stockMainService;  
    } 
	public void setWormService(WormService wormService) {
        this.wormService = wormService;  
    } 
	      
	    @PostConstruct  
	    public void init() {  
	    	stockInitWork = this;  
	    	stockInitWork.stockDaykService = this.stockDaykService;  
	    	stockInitWork.wormService = this.wormService;  
	    	stockInitWork.stockMainService = this.stockMainService;  
	    } 
	
	//private CountDownLatch threadsSignal;
	private String stock_code;//股票代码
	public StockInitWork(){
		
	}
	public StockInitWork(String stock_code) {
		//this.threadsSignal = threadsSignal;
		this.stock_code=stock_code;
	}
	//获取股票信息并更新它
	@Override
	public void run() {
		try {
			stockInitWork.stockMainService.saveStock(stock_code);
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	 
}
