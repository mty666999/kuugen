package com.kuugen.modular.work.test.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kuugen.core.util.DateUtils;
import com.kuugen.core.util.ResultMsg;
import com.kuugen.modular.stock.model.TbStockMainModel;
import com.kuugen.modular.stock.stockinfo.mapper.StockMainMapper;
import com.kuugen.modular.work.ipconfig.mapper.TbWorkIpMapper;
import com.kuugen.modular.work.ipconfig.model.TbWorkIpModel;
import com.kuugen.modular.work.test.service.WorkTestService;

import com.kuugen.modular.work.thread.StockHisDataWork;
import com.kuugen.modular.worm.util.ProxyCralwerUnusedVPN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
 
@Service(value="workTestService")
public class WorkTestServiceImpl  implements WorkTestService {

	@Autowired
	private TbWorkIpMapper tbWorkIpMapper;
	@Autowired
	private StockMainMapper stockMainMapper;
	@Override
	public ResultMsg initIP() {
		// TODO Auto-generated method stub
		ResultMsg msg =new ResultMsg();
		 ProxyCralwerUnusedVPN proxyCrawler = new ProxyCralwerUnusedVPN();
        /**
         * 想要获取的代理IP个数，由需求方自行指定。（如果个数太多，将导致返回变慢）
         */
		 
        String ipstr= proxyCrawler.startCrawler(5);
        Map map=   (Map) JSONObject.parse(ipstr);
        List<Map> iplist =(List<Map>) map.get("proxy");
        for(int i=0;i<iplist.size();i++){
        	Map ip = iplist.get(i);
        	//判断ip是否存在,然后更新
        	int count = tbWorkIpMapper.getWorkIpCount((String)ip.get("ip"));
        	TbWorkIpModel ipinfo =new TbWorkIpModel(ip);
        	if(count ==0){
        		tbWorkIpMapper.addWorkIp(ipinfo);
        	}
        }
			
		 
        msg.setSuccess(true);
        msg.setMessage("初始化成功");
		return msg;
	}

	@Override
	public void initHisStock() {
		// TODO Auto-generated method stub
		//首先获取全部的没有初始化数据的股票列表
		List<TbStockMainModel> stocklist =stockMainMapper.getNotInitStock();
		//获取当前年月
		int year =DateUtils.getNowYear();
		int jidu =DateUtils.getNowJidu();
		ExecutorService exec = Executors.newCachedThreadPool();
		//遍历股票然后分季度获取数据
		for(int i=0;i<stocklist.size();i++){
//		for(int i=0;i<1;i++){
			String stock_code=stocklist.get(i).getStock_code();
//			String stock_code="300003";
			//获取3年中的数据,年份遍历 j=年份
			for(int j=year -1;j<=year;j++ ){
				if(year==j){
					//今年度的就以当前季度作为最大,季度遍历 n=季度
					for(int n=1;n<=jidu;n++){
						StockHisDataWork work=new StockHisDataWork( stock_code, j, n);
						exec.submit(work);
					}
				}else{
					//往年度的就1-4,季度遍历 n=季度
					for(int n=1;n<=4;n++){
						StockHisDataWork work=new StockHisDataWork( stock_code, j, n);
						exec.submit(work);
					}
				}
			}
		}
		
		exec.shutdown();
		try {
			exec.awaitTermination(1, TimeUnit.HOURS);
			System.out.println("结束");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("超时");
		}
	}

	@Override
	public void insertLog() {
		// TODO Auto-generated method stub
		String [] stock_list={"300059","000321","601992","300827","307221","121332"};
		
		Random rand = new Random();
		long beginTime = System.currentTimeMillis();  
		//循环插入10w个list
		for(int i=0;i<300;i++){
			List<Map> datalist=new LinkedList<>();
			for(int j=0;j<10000;j++){
				Map map=new HashMap<String,String>();
				map.put("stock_code", stock_list[rand.nextInt(6)]);
				map.put("value", Math.random()*20);
				datalist.add(map);
			}
			stockMainMapper.addLogs(datalist);
		}
		
		long endTime = System.currentTimeMillis();  
		System.out.println("一共花费的时间：" + (endTime - beginTime));  
	}
	 

}
