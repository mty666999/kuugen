package com.kuugen.modular.stock.stockinfo.mapper;

import com.kuugen.modular.stock.model.TbStockMainModel;
import com.kuugen.modular.stock.model.TbUserStock;

import java.util.List;
import java.util.Map;


public interface StockMainMapper {

	 

	void addStockMain(Map<String, String> mainMap);

	int getStockCount(Map<String, String> mainMap);

	void updateStockMain(Map<String, String> mainMap);

	List<TbStockMainModel> getStockList(Map paramap);

	int getAllStockCount(Map paramap);

	List<TbStockMainModel> getNotInitStock();

	void addLogs(List<Map> datalist);

	List<TbUserStock> getUserList(String stock_code);

	 
}
