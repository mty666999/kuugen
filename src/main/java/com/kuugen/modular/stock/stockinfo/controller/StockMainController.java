package com.kuugen.modular.stock.stockinfo.controller;

import javax.servlet.http.HttpServletRequest;


import cn.hutool.core.util.StrUtil;
import com.kuugen.core.util.ResultMsg;
import com.kuugen.core.util.StringUtil;
import com.kuugen.modular.stock.stockinfo.service.StockMainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;



/**
 * 股票主表控制类
 * @author mty
 *
 */
@Controller
@RequestMapping(value = "/stock/main", produces = "text/html;charset=UTF-8")
public class StockMainController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(StockMainController.class);
	@Autowired
	private StockMainService stockMainService;
	
	/**
	 * 股票首页
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, ModelMap modelMap) {
		 
		return "/stock/stockinfo/stock_index";
	}
	
	/**
	 * 批量新增股票
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/addStockList")
	public String addStockList(HttpServletRequest request, ModelMap modelMap) {
		 
		return "/stock/stockinfo/add_stockList";
	}
	/**
	 * 获取jq的股票列表
	 * @param request
	 * @return
	 */
	/*@RequestMapping("/getStockList")
	@ResponseBody
	public ResultMsg getStockList(HttpServletRequest request, JQGridRequest jqRes){
		System.out.println(JSON.toJSONString(jqRes));
		ResultMsg msg=new ResultMsg();
		JqGrid jqGrid=stockMainService.getStockList(jqRes);
		//返回股票数据列表
		msg.setData(jqGrid);
		return msg;
	}*/
	
	@RequestMapping("/saveStock")
	@ResponseBody
	public ResultMsg saveStock(HttpServletRequest request, String stocks) {
		ResultMsg msg=new ResultMsg();
		 
		//验证参数是否正确
		if(!StrUtil.isEmpty(stocks)&&StringUtil.checkStockStr(stocks)){
			msg = stockMainService.saveStock(stocks);
		}else{
			msg.setMessage("股票不能为空或者格式不对");
			msg.setSuccess(false);
		}
		
		return msg;
	}
}
