package com.kuugen.modular.stock.stockinfo.service.impl;

import com.kuugen.core.util.DateUtils;
import com.kuugen.core.util.ResultMsg;
import com.kuugen.modular.stock.model.TbStockMainModel;
import com.kuugen.modular.stock.stockinfo.mapper.StockMainMapper;
import com.kuugen.modular.stock.stockinfo.service.StockMainService;

import com.kuugen.modular.stock.thread.StockContrastWork;
import com.kuugen.modular.work.thread.StockHisDataWork;
import com.kuugen.modular.worm.service.WormService;
import com.kuugen.modular.worm.util.html.SinaStockUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service(value="stockMainService")
public class StockMainServiceImpl  implements StockMainService {

	/**
	 * Logger for this class
	 */
	private static final Log log = LogFactory.getLog(StockMainServiceImpl.class);
	@Autowired
	private WormService wormService;
	@Autowired
	private StockMainMapper stockMainMapper;
	
	/**
	 * 出现异常数据回滚
	 */
	@Override
	@Transactional(rollbackForClassName="Exception")
	public ResultMsg saveStock(String stocks) {
		ResultMsg msg=new ResultMsg();
		Map stockMap=new HashMap<>();
		Map<String,String> mainMap=new HashMap<>();
		//对股票代码进行解析,并获取股票概况,加入股票表
		String[] stocklist=stocks.split(",");
		for(int i=0;i<stocklist.length;i++){
			stockMap = SinaStockUtil.getStockMainData(stocklist[i]);
			mainMap = (Map) stockMap.get("mainData");
			//首先判断是否存在
			int count = stockMainMapper.getStockCount(mainMap);
			if(count>0){
				//更新股票基础信息
				stockMainMapper.updateStockMain(mainMap);
			}else{
				//保存股票基础信息
				stockMainMapper.addStockMain(mainMap);
				//这里需要获取当前一季度的股票数据
				//获取当前年月
				int year =DateUtils.getNowYear();
				int jidu =DateUtils.getNowJidu();
				StockHisDataWork work=new StockHisDataWork( mainMap.get("stock_code"), year, jidu);
				work.run();
				StockContrastWork swork=new  StockContrastWork(mainMap);
				swork.run();
			}
			
		}
		
		msg.setMessage("保存成功");
		return msg;
	}

	/**
	 * 获取股票列表
	 * @return
	 */
	@Override
	public List<Map<String,String>> getStockList() {
		return stockMainMapper.getStockListAll();
	}

	/*@Override
	public JqGrid getStockList(JQGridRequest jqRes) {
		// TODO Auto-generated method stub
		JqGrid gridData=new JqGrid();
		Map paramap=new HashMap<>();
		jqRes.initPage();
		paramap.put("jqRes", jqRes);
		//首先获取分页股票数据
		List<TbStockMainModel> stocklist=stockMainMapper.getStockList(paramap);
		//然后获取总数并计算当前页等数据
		int count =stockMainMapper.getAllStockCount(paramap);
		gridData.setRows(stocklist);
		gridData.initPage(count, jqRes.getPage(), jqRes.getRows());
		return gridData;
	}*/

	/*@Override
	public List<TbUserStock> getUserList(String stock_code) {
		// TODO Auto-generated method stub
		return stockMainMapper.getUserList(stock_code);
	}*/

	 

}
