package com.kuugen.modular.stock.avgrage.mapper;

import com.kuugen.modular.stock.model.TbStockAvgrageModel;


public interface StockAvgrageMapper {

	int getCount(TbStockAvgrageModel stockAvginfo);

	void update(TbStockAvgrageModel stockAvginfo);

	void insert(TbStockAvgrageModel stockAvginfo);


}
