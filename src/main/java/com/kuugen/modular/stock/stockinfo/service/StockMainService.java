package com.kuugen.modular.stock.stockinfo.service;

import com.kuugen.core.util.ResultMsg;
import com.kuugen.modular.stock.model.TbStockMainModel;

import java.util.List;
import java.util.Map;


public interface  StockMainService {

	ResultMsg saveStock(String stocks);

    List<Map<String,String>>  getStockList();

    //JqGrid getStockList(JQGridRequest jqRes);

//	List<TbUserStock> getUserList(String stock_code);
 
}
