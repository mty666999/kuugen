package com.kuugen.modular.work.test.controller;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kuugen.core.common.exception.WormException;
import com.kuugen.core.util.ResultMsg;
import com.kuugen.modular.stock.dayk.service.StockDaykService;
import com.kuugen.modular.work.test.service.WorkTestService;
import com.kuugen.modular.worm.service.WormService;
import com.kuugen.modular.worm.util.html.SinaStockUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;




/**
 * 作业测试,调用类
 * @author mty
 *
 */
@Controller
@RequestMapping(value = "/work/test", produces = "text/html;charset=UTF-8")
public class WorkTestController {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(WorkTestController.class);
	@Autowired
	private WorkTestService workTestService;
	@Autowired
	private WormService wormService;
	@Autowired
	private StockDaykService stockDaykService;
	
	
	/**
	 * 作业测试首页
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, ModelMap modelMap) {
		 
		return "/work/test/test_index";
	}
	
	/**
	 * 初始化代理ip
	 * @param request
	 * @return
	 */
	@RequestMapping("/initIP")
	@ResponseBody
	public ResultMsg initIP(HttpServletRequest request) {
		ResultMsg msg=new ResultMsg();
		msg = workTestService.initIP();
		return msg;
	}
	
	/**
	 * 初始化股票数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/initStock")
	@ResponseBody
	public ResultMsg initStock(HttpServletRequest request) {
		ResultMsg msg=new ResultMsg();
		StringBuilder url=new StringBuilder();
		url.append("http://hq.sinajs.cn/list=");
		url.append("sz002555");
		url.append(",sz300059");
		url.append(",sh603708");
		//首先获取返回doc内容
		Document doc=wormService.getHtmlForUrl(url.toString(),0);
		if(doc != null){
			List<Map<String, String>> stocklist= SinaStockUtil.getNowStockData(doc);
			
		}else{
			msg.setSuccess(false);
			msg.setMessage("获取失败");
		}
		return msg;
	}
	/**
	 * 初始化股票历史数据,自动初始化历史数据为空的股票的数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/initHisStock")
	@ResponseBody
	public ResultMsg initHisStock(HttpServletRequest request) {
		ResultMsg msg=new ResultMsg();
		/*String stock_code ="300059";
		ResultMsg msg=new ResultMsg();
		StringBuilder url =new StringBuilder();
		url.append("http://money.finance.sina.com.cn/corp/go.php/vMS_MarketHistory/stockid/");
		url.append(stock_code).append(".phtml?");
		url.append("year=2018");
		url.append("&jidu=1");
		//首先获取返回doc内容
		Document doc = wormService.getHtmlForUrl(url.toString());
		if(doc != null){
			List<Map<String, String>> stocklist= SinaStockUtil.getHistotyStockData(doc);
			msg = stockDaykService.saveHisData(stocklist,stock_code);
		}else{
			msg.setSuccess(false);
			msg.setMessage("获取失败");
		}*/
		try {
			workTestService.initHisStock();
		}catch(WormException e ){
			logger.error(e.getMessage());
			logger.error(e.getStackTrace());
			msg.setSuccess(false);
			msg.setMessage(e.getMessage());
		}catch(Exception e ){
			logger.error(e.getMessage());
			logger.error(e.getStackTrace());
			msg.setSuccess(false);
			msg.setMessage("系统错误");
		}
		return msg;
	}
	
	@RequestMapping("/insertLog")
	@ResponseBody
	public ResultMsg insertLog(HttpServletRequest request) {
		ResultMsg msg=new ResultMsg();
		 
		try {
			workTestService.insertLog();
		}catch(WormException e ){
			logger.error(e.getMessage());
			logger.error(e.getStackTrace());
			msg.setSuccess(false);
			msg.setMessage(e.getMessage());
		}catch(Exception e ){
			logger.error(e.getMessage());
			logger.error(e.getCause());
			logger.error(e.getStackTrace());
			msg.setSuccess(false);
			msg.setMessage("系统错误");
		}
		return msg;
	}
}


