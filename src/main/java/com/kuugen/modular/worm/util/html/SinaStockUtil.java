package com.kuugen.modular.worm.util.html;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kuugen.core.common.exception.WormException;
import com.kuugen.core.util.GB2Alpha;
import com.kuugen.core.util.StringUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



/**
 * 新浪接口的html内容解析类
 * @author mty
 *
 */
public class SinaStockUtil {
	/**
	 * 获取股票概况url *是替换符
	 */
	private static final String getSinaMainUrl="http://finance.sina.com.cn/realstock/company/*/nc.shtml";
	//东方财富网页数据
	private static final String getDfMainUrl="http://quote.eastmoney.com/*.html";
	//百度数据
	private static final String getBdMainUrl="https://gupiao.baidu.com/stock/*.html";
	//新浪数据接口
	private static final String sinaDataUrl="http://hq.sinajs.cn/list=s_*";
	//名称和字段的对比
	private static final HashMap<String, String> filedmap;
	static {
		filedmap =new HashMap<>();
		  
		filedmap.put("市盈率MRQ", "pe");
		filedmap.put("总股本", "tol_stocks");
		filedmap.put("总市值", "tol_val");
		filedmap.put("市净率", "pb");
		filedmap.put("每股净资产", "net_assets");
		filedmap.put("流通股本", "flow_stocks");
	}
	
	/**
	 * 获取当天的股票信息 这个是单条独自用的
	 * sina数据
	 * @param doc
	 * @return
	 */
	public static List<Map<String, String>> getNowStockDataShort(Document doc){
		List<Map<String,String>> stocklist=new ArrayList<>();
		if(doc!=null){
			Element body = doc.body();
			String data =body.text();
			for(String stock_str:data.split(";")){
				Map<String,String> stockMap =new HashMap<String, String>();
				String []stockinfo=stock_str.split("=");
				//获取股票编号
				String stock_code="";//stockinfo[0].substring(13);
				if(stockinfo[0].indexOf("hq_str_")>0){
					stock_code = stockinfo[0].substring(stockinfo[0].indexOf("hq_str_")+9);
				}
				
				//获取当日信息
				String now_data=stockinfo[1].replace("\"", "");
				stockMap.put("stock_code", stock_code.substring(2));
				String []maininfo=now_data.split(",");
				stockMap.put("stock_name", maininfo[0]);
				stockMap.put("now_price", maininfo[1]);
				stockMap.put("volume", maininfo[4]);
				stockMap.put("stock_name_en", GB2Alpha.String2Alpha(maininfo[0]));
				
				//如果当日信息是0.0的话就跳过
				if("0.000".equals(maininfo[1])){
					continue;
				}
				stocklist.add(stockMap);
			}
		}
		return stocklist;
	}
	
	
	/**
	 * 获取当天的股票信息 全部数据
	 * sina数据
	 * @param doc
	 * @return
	 */
	public static List<Map<String, String>> getNowStockData(Document doc){
		List<Map<String,String>> stocklist=new ArrayList<>();
		if(doc!=null){
			Element body = doc.body();
			String data =body.text();
			for(String stock_str:data.split(";")){
				Map<String,String> stockMap =new HashMap<String, String>();
				String []stockinfo=stock_str.split("=");
				//获取股票编号
				String stock_code="";//stockinfo[0].substring(13);
				if(stockinfo[0].indexOf("hq_str_")>0){
					stock_code = stockinfo[0].substring(stockinfo[0].indexOf("hq_str_")+9);
				}
				
				//获取当日信息
				String now_data=stockinfo[1].replace("\"", "");
				stockMap.put("stock_code", stock_code);
				String []maininfo=now_data.split(",");
				stockMap.put("stock_name", maininfo[0]);
				stockMap.put("now_price", maininfo[3]);
				stockMap.put("volume", maininfo[8].substring(0,maininfo[8].length()-2));
				stockMap.put("stock_name_en", GB2Alpha.String2Alpha(maininfo[0]));
				
				//如果当日信息是0.0的话就跳过
				if("0.000".equals(maininfo[1])){
					continue;
				}
				stocklist.add(stockMap);
			}
		}
		return stocklist;
	}
	
	/**
	 * 获取历史的股票信息(一次获取一只股票一个季度的数据)
	 * @param doc
	 * @return
	 */
	public static List<Map<String, String>> getHistotyStockData(Document doc){
		List<Map<String,String>> stocklist=new ArrayList<>();
		if(doc!=null){
			Element body = doc.body();
			Element table =body.getElementById("FundHoldSharesTable");
			Elements trs=table.getElementsByTag("tr");
			for(int i=0;i<trs.size();i++){
				if(i<2){
					continue;
				}
				Map<String,String> stockMap =new HashMap<String, String>();
				String[] strs=trs.get(i).text().split(" ");
				stockMap.put("stock_date", strs[0]);
				stockMap.put("open_price", strs[1]);
				stockMap.put("high_pice", strs[2]);
				stockMap.put("close_price", strs[3]);
				stockMap.put("low_price", strs[4]);
				stockMap.put("vol_num", strs[5]);
				stockMap.put("vol_amount", strs[6]);
				stocklist.add(stockMap);
			}
		}
		return stocklist;
	}
	
	/**
	 * 获取股票概况信息(单只股票独立获取)
	 * @param doc
	 * @return
	 */
	public static Map<String, Object> getStockMainData(String stockCode){
		Map<String, Object> stockData=new HashMap<>();
		//主要股票概况
		Map<String, String> mainData=new HashMap<String, String>();
		Connection con =null;
		Document doc =null;
		String trueCode =getTrueCode(stockCode,"");
		//首先获取sina数据判断是否有股票
		String url =sinaDataUrl.replace("*", trueCode);
		//获取请求连接
        con = Jsoup.connect(url.toString()); 
        //解析请求结果
        try {
			doc=con.ignoreContentType(true).get();
			mainData = getNowStockDataShort(doc).get(0);
			
			mainData.put("stock_type", trueCode.substring(0,2));
			url =getBdMainUrl.replace("*", trueCode);
			//获取请求连接
	        con = Jsoup.connect(url.toString()); 
	        //解析请求结果
        
			doc = con.ignoreContentType(true).get();  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new WormException("请求url: "+url+"数据解析失败");
		} catch (IndexOutOfBoundsException e){
			throw new WormException("请求url: "+url+"的股票不存在");
		}
		Element body=doc.body();
		Elements tables=body.getElementsByClass("bets-content");
		String data =tables.text();
		String[] paras=data.split(" ");
		//然后开始解析数据,数据两个一组,所以+=2
		for(int i=0;i<paras.length;i+=2){
			//不为空的就是需要的字段
			if(!StringUtil.isEmpty(filedmap.get(paras[i]))){
				mainData.put(filedmap.get(paras[i]), paras[i+1]);
			}
		}
		stockData.put("mainData", mainData);
		return stockData;
	}
	/**
	 * 根据代码判断股票在那个板块,并返回拼接后数据
	 * @param stockCode
	 * @param tab分隔符
	 * @return
	 */
	public static String getTrueCode(String stockCode,String tab){
		String trueCode ="";
		if(!StringUtil.isEmpty(stockCode)){
			//深圳(0)或创业板(3)是 sz
			if(stockCode.startsWith("0")||stockCode.startsWith("3")){
				trueCode="sz"+tab+stockCode;
			//上证(6)是sh
			}else if(stockCode.startsWith("6")){
				trueCode="sh"+tab+stockCode;
			}else{
				trueCode=stockCode;
			}
		}
		return trueCode;
	}
}
