package com.kuugen.modular.stock.thread;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;


import com.kuugen.core.util.DateUtils;
import com.kuugen.core.util.JsonUtil;
import com.kuugen.core.util.StringUtil;
import com.kuugen.modular.stock.avgrage.service.StockAvgrageService;
import com.kuugen.modular.stock.dayk.service.StockDaykService;
import com.kuugen.modular.stock.model.TbNormDataModel;
import com.kuugen.modular.stock.model.TbStockAvgrageModel;
import com.kuugen.modular.stock.model.TbStockDaykModel;
import com.kuugen.modular.stock.model.TbUserStock;
import com.kuugen.modular.stock.norm.mapper.NormMapper;
import com.kuugen.modular.stock.stockinfo.service.StockMainService;
import com.kuugen.modular.worm.service.WormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * 股票对比的多线程实现类
 * @author mty
 *
 */
@Component 
public class StockContrastWork implements Runnable{
	
	private static final Logger logger =  LoggerFactory.getLogger(StockContrastWork.class);
	@Autowired
	private StockDaykService stockDaykService;
	@Autowired
	private StockMainService stockMainService;
	@Autowired
	private WormService wormService;
	@Autowired
	private NormMapper normMapper;
	
	@Autowired
	private StockAvgrageService stockAvgrageService;
	private static StockContrastWork stockContrastWork;  
	  
	public void setStockDaykService(StockDaykService  stockDaykService) {  
        this.stockDaykService = stockDaykService;  
    } 
	public void setStockMainService(StockMainService  stockMainService) {  
        this.stockMainService = stockMainService;  
    } 
	public void setNormMapper(NormMapper normMapper) {
        this.normMapper = normMapper;  
    }
	
	
	public void setStockAvgrageService(StockAvgrageService stockAvgrageService) {
		this.stockAvgrageService = stockAvgrageService;
	}
	public void setWormService(WormService  wormService) {  
        this.wormService = wormService;  
    } 
	      
    @PostConstruct  
    public void init() {  
    	stockContrastWork = this;  
    	stockContrastWork.stockDaykService = this.stockDaykService;  
    	stockContrastWork.wormService = this.wormService;  
    	stockContrastWork.stockMainService = this.stockMainService; 
    	stockContrastWork.stockAvgrageService = this.stockAvgrageService;
    	stockContrastWork.normMapper = this.normMapper;
    } 
	
	//private CountDownLatch threadsSignal;
	private Map<String,String> stock_map;//股票代码
	public StockContrastWork(){
		
	}
	public StockContrastWork(Map<String,String> stock_map) {
		//this.threadsSignal = threadsSignal;
		this.stock_map=stock_map;
	}
	//获取股票信息并更新它
	@Override
	public void run() {
		try {
			System.out.println("获取到:" +JsonUtil.map2json(stock_map));
			//获取到股票后,先计算当天的13均线数据
			String stock_code =stock_map.get("stock_code");
			//1获取,前12天的日k数据
			List<TbStockDaykModel> days= stockContrastWork.stockDaykService.getDayKlist(stock_map.get("stock_code"),DateUtils.getDateStr(), 12);
			double avg_price= StringUtil.getAvgPrice(days,stock_map.get("now_price"));
			TbStockAvgrageModel stockAvginfo=new TbStockAvgrageModel();
			stockAvginfo.setStock_code(stock_code);
			stockAvginfo.setDay_num("13");
			stockAvginfo.setAve_price(avg_price+"");
			stockAvginfo.setDate(DateUtils.getDateStr());
			stockAvginfo.setUpdate_time(DateUtils.getDateString(new Date(),"yyyy-MM-dd HH:mm:ss"));
			stockContrastWork.stockAvgrageService.saveAvgData(stockAvginfo);
			//当前价和均线价进行对比
			boolean flag =StringUtil.ifOvenPrice(stock_map.get("now_price"), avg_price+"");
			//获取当前的均线状态
			TbNormDataModel nromMap = new TbNormDataModel();
			nromMap.setNorm_id("1");
			nromMap.setStock_code(stock_code );
			if(flag){
				nromMap.setState("Y");
			}else{
				nromMap.setState("N");
			}
			TbNormDataModel norminfo = stockContrastWork.normMapper.getNorminfo(nromMap);
			//如果是新数据没有的话直接新增,然后结束
			if(norminfo==null){
				stockContrastWork.normMapper.insert(nromMap);
				stockContrastWork.normMapper.insertlog(nromMap);
				return ;
			}
			//只有状态变化才会发消息
			if(flag){
				nromMap.setState("Y");
				if("N".equals(norminfo.getState())){
					msgHandel(nromMap);
				}
			}else{
				nromMap.setState("N");
				if("Y".equals(norminfo.getState())){
					msgHandel(nromMap);
				}
			}
			stockContrastWork.normMapper.update(nromMap);
			stockContrastWork.normMapper.insertlog(nromMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	//消息处理
	private void msgHandel(TbNormDataModel nromMap) {
		// TODO Auto-generated method stub
		String stock_code = nromMap.getStock_code();
		//根据股票获取用户id列表
		List<TbUserStock> userLit=null;//stockContrastWork.stockMainService.getUserList(stock_code);
		for(int i=0;i<userLit.size();i++){
			TbUserStock userinfo=userLit.get(i);
			String toUser = userinfo.getOpen_id();
			String stock_name =userinfo.getStock_name();
			String tmpId =  "";
			String topcolor = "#FF0000";
			if("Y".equals(nromMap.getState())){
				tmpId="xo8NqUcuyXWxgw_3faaAardWf5MataWQBt0v0rNLOBs";
			}else{
				tmpId="-uhfA-oqJ5_GvaX7QyKLPj7xZ6z3-bHOvLJN-th4qhA";
			}
			/*String url = ConfKit.get("WebSite_Url")  ;
			Template data = new Template(toUser, tmpId, url, topcolor);
			Map<String, TemplateItem> item = new HashMap<String, TemplateItem>();
			item.put("stock_name", new TemplateItem(stock_name));
			data.setData(item);
			TemplateAPI tmpAPI = new TemplateAPI(APIConfigUtils.getApiConfig());
			String str = tmpAPI.sendTemplateData(data).toString();
			logger.info(str);*/
		}
		//		String toUser = "oN5BC1nBn-hZGYVd6SmAs-OgK6cE";
//		String tmpId = "xo8NqUcuyXWxgw_3faaAardWf5MataWQBt0v0rNLOBs"; //
//		String topcolor = "#FF0000";
//		String url = ConfKit.get("WebSite_Url")  ;
//		Template data = new Template(toUser, tmpId, url, topcolor);
//		Map<String, TemplateItem> item = new HashMap<String, TemplateItem>();
//		item.put("stock_name", new TemplateItem("东方财富"));
//		 
//		data.setData(item);
//		TemplateAPI tmpAPI = new TemplateAPI(APIConfigUtils.getApiConfig());
//		String str = tmpAPI.sendTemplateData(data).toString();
//		logger.info(str);
	}
	 
}
