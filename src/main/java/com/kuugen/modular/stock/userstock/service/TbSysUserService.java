package com.kuugen.modular.stock.userstock.service;

import com.kuugen.core.util.ResultMsg;
import com.kuugen.modular.stock.model.TbStockMainModel;
import com.kuugen.modular.stock.model.TbSysUserModel;

import java.util.List;


public interface  TbSysUserService {


	TbSysUserModel getUserByOpenId(String fromUserName);

	List<TbStockMainModel> getUserStockByOpenId(String fromUserName);

	ResultMsg gzStock(String user_id, String s);

	ResultMsg bindWx(String openId, String s);

	List<TbStockMainModel> getUserStrongStock(String fromUserName);

	List<TbStockMainModel> getUserWeakStock(String fromUserName);
}
