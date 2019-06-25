package com.kuugen.modular.stock.dayk.service;

import com.kuugen.core.util.ResultMsg;
import com.kuugen.modular.stock.model.TbStockDaykModel;

import java.util.List;
import java.util.Map;



public interface  StockDaykService {

	ResultMsg saveHisData(List<Map<String, String>> stocklist, String stock_code, int year, int jidu);

	List<TbStockDaykModel> getDayKlist(String stock_code, String date,
									   int num);
 
}
