package com.kuugen.modular.stock.userstock.service.impl;

import com.kuugen.core.util.ResultMsg;
import com.kuugen.core.util.StringUtil;
import com.kuugen.core.util.UUIDGenerator;
import com.kuugen.modular.stock.model.TbStockMainModel;
import com.kuugen.modular.stock.model.TbSysUserModel;
import com.kuugen.modular.stock.stockinfo.mapper.StockMainMapper;
import com.kuugen.modular.stock.thread.StockInitWork;
import com.kuugen.modular.stock.userstock.mapper.TbSysUserMapper;
import com.kuugen.modular.stock.userstock.service.TbSysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service(value="tbSysUserService")
public class TbSysUserServiceImpl implements TbSysUserService {
	@Autowired
	private TbSysUserMapper tbSysUserMapper;
	@Autowired
	private StockMainMapper stockMainMapper;

	@Override
	public TbSysUserModel getUserByOpenId(String fromUserName) {
		return tbSysUserMapper.getUserByOpenId(fromUserName);
	}

	@Override
	public List<TbStockMainModel> getUserStockByOpenId(String fromUserName) {
		return tbSysUserMapper.getUserStockByOpenId(fromUserName);
	}

	@Override
	public ResultMsg gzStock(String user_id, String stock_code) {
		// TODO Auto-generated method stub
		ResultMsg msg=new ResultMsg();
		//首先判断openid在不在,在的话更新不在就新增
		Map<String,String> map =new HashMap<>();
		map.put("user_id", user_id);
		map.put("stock_code", stock_code);

		int count = tbSysUserMapper.getGzStockCount(map);
		if(count==0){
			//如果是一只新股票,那么要开启线程去初始化它
			if(0==stockMainMapper.getStockCount(map)){
				StockInitWork initStock = new StockInitWork(stock_code);

				new Thread(initStock).start();
			}
			tbSysUserMapper.insertUserStock(map);
			msg.setMessage("关注成功");

		}else if(count==1){
			msg.setMessage("已关注该股票");
		}else{
			msg.setMessage("结束");
		}
		return msg;
	}

	@Override
	public ResultMsg bindWx(String openId, String name) {
		ResultMsg msg=new ResultMsg();
		//首先判断openid在不在,在的话更新不在就新增
		Map<String,String> map =new HashMap<>();
		map.put("open_id", openId);
		map.put("user_name", name);
		int flag= tbSysUserMapper.getCountByOpenid( map);
		if(flag==0){
			map.put("user_id", UUIDGenerator.generate());
			tbSysUserMapper.insertWxUser(map);
			msg.setMessage("绑定成功");
		}else if(flag==1){
			tbSysUserMapper.updateWxUser(map);
			msg.setMessage("已更新绑定信息");
		}else{
			msg.setMessage("用户已绑定");
		}
		return msg;
	}

	@Override
	public List<TbStockMainModel> getUserStrongStock(String fromUserName) {
		return tbSysUserMapper.getUserStrongStock(fromUserName);
	}

	@Override
	public List<TbStockMainModel> getUserWeakStock(String fromUserName) {
		return tbSysUserMapper.getUserWeakStock(fromUserName);
	}
}
