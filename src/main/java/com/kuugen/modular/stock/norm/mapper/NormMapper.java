package com.kuugen.modular.stock.norm.mapper;

import com.kuugen.modular.stock.model.TbNormDataModel;



public interface NormMapper {

	TbNormDataModel getNorminfo(TbNormDataModel norminfo);

	void insert(TbNormDataModel nromMap);

	void insertlog(TbNormDataModel nromMap);

	void update(TbNormDataModel nromMap);

 


}
