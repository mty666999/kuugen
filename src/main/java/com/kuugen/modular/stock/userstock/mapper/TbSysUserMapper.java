package com.kuugen.modular.stock.userstock.mapper;

import com.kuugen.modular.stock.model.TbStockMainModel;
import com.kuugen.modular.stock.model.TbSysUserModel;

import java.util.List;
import java.util.Map;

public interface TbSysUserMapper {



	TbSysUserModel getUserByOpenId(String open_id);

	List<TbStockMainModel> getUserStockByOpenId(String open_id);

	List<TbStockMainModel> getUserStrongStock(String open_id);

	List<TbStockMainModel> getUserWeakStock(String   open_id);

	int getCountByOpenid(Map<String,String> map);

	void insertWxUser(Map<String,String> map);

	void updateWxUser(Map<String,String> map);

	void insertUserStock(Map<String,String> map);

	int getGzStockCount(Map<String,String> map);
}
