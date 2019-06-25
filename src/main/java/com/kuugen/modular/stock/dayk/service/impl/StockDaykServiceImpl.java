package com.kuugen.modular.stock.dayk.service.impl;


import com.kuugen.core.util.ResultMsg;
import com.kuugen.modular.stock.dayk.mapper.StockDaykMapper;
import com.kuugen.modular.stock.dayk.service.StockDaykService;
import com.kuugen.modular.stock.model.TbStockDaykModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service(value="stockDaykService")
public class StockDaykServiceImpl  implements StockDaykService {

	@Autowired
	private StockDaykMapper stockDaykMapper;
	@Override
	public ResultMsg saveHisData(List<Map<String, String>> stocklist , String stock_code, int year, int jidu) {
		// TODO Auto-generated method stub
		ResultMsg msg =new ResultMsg();
		Map queryMap =new HashMap<String, String>();
		if(stocklist!=null&&stocklist.size()>0){
			queryMap.put("stock_code", stock_code);
			queryMap.put("year", year+"");
			queryMap.put("jidu", jidu+"");
			//首先删除数据然后进行新增
			stockDaykMapper.deleteDaykByjd(queryMap);
			for(Map<String,String> map:stocklist){
				map.put("stock_code", stock_code);
				map.put("year", year+"");
				map.put("jidu", jidu+"");
				stockDaykMapper.addDayk(map);
			}
		}else{
			msg.setSuccess(false);
			msg.setMessage("获取数据失败");
		}
		return msg;
	}
	@Override
	public List<TbStockDaykModel> getDayKlist(String stock_code, String date,
											  int num) {
		// TODO Auto-generated method stub
		//根据股票代码,和时间获取前n天的数据
		Map queryMap =new HashMap<String, String>();
		queryMap.put("stock_code", stock_code);
		queryMap.put("date", date);
		queryMap.put("num", num);
		
		return stockDaykMapper.getDayKlist(queryMap);
	}

	 
}
