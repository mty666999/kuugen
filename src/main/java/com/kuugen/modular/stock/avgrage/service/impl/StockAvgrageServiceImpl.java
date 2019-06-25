package com.kuugen.modular.stock.avgrage.service.impl;

import com.kuugen.modular.stock.avgrage.mapper.StockAvgrageMapper;
import com.kuugen.modular.stock.avgrage.service.StockAvgrageService;

import com.kuugen.modular.stock.model.TbStockAvgrageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service(value="stockAvgrageService")
public class StockAvgrageServiceImpl  implements StockAvgrageService {

	@Autowired
	private StockAvgrageMapper stockAvgrageMapper;
	
	@Override
	public void saveAvgData(TbStockAvgrageModel stockAvginfo) {
		// TODO Auto-generated method stub
		//先判断是否存在改均线数据,不存在就新增
		int flag=stockAvgrageMapper.getCount(stockAvginfo);
		if(flag==1){
			stockAvgrageMapper.update(stockAvginfo);
		}else if(flag==0){
			stockAvgrageMapper.insert(stockAvginfo);
		}else{
			
		}
	}

	 

}
