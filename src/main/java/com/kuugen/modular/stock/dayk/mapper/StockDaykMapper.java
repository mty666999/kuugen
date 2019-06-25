package com.kuugen.modular.stock.dayk.mapper;

import com.kuugen.modular.stock.model.TbStockDaykModel;

import java.util.List;
import java.util.Map;




public interface StockDaykMapper {

	void addDayk(Map<String, String> map);

	void deleteDaykByjd(Map queryMap);

	List<TbStockDaykModel> getDayKlist(Map queryMap);


}
