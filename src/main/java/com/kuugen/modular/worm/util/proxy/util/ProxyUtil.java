package com.kuugen.modular.worm.util.proxy.util;



import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
  * @ClassName: ProxyUtil
  * @Description: 代理IP工具 ping百度首页
  * @author ex-hutao001
  * @date 2017年9月29日 16:45:56
  */
public class ProxyUtil
{
    /**
     * @Title: checkProxy 
     * @Description: 对请求过来的ip地址进行一次过滤
     * @param ip 代理IP
     * @param port 端口号
     * @return boolean 
     * @author ex-hutao001
     * @date 2017年9月30日
     */
    public static boolean checkProxy(String ip, Integer port)
    {
        try {
        	//获取请求连接
            Connection con = Jsoup.connect("http://hq.sinajs.cn/list=sz002555").proxy(ip, port); 
            //解析请求结果
            Document doc=null;
            doc = con.ignoreContentType(true).get();  
        	//验证是否可用
            if(doc.text().indexOf("无效用户")>=0||doc.text().indexOf("有道")>=0){
            	return false;
            }
            
            //然后判断另一个地址
            StringBuffer url =new StringBuffer();
    		url.append("http://money.finance.sina.com.cn/corp/go.php/vMS_MarketHistory/stockid/");
    		url.append("000001").append(".phtml?");
    		url.append("year="+"2018");
    		url.append("&jidu="+"1");
    		con = Jsoup.connect(url.toString()).proxy(ip, port); 
            //解析请求结果
            doc = con.ignoreContentType(true).get();  
            if(doc.text().indexOf("无效用户")>=0||doc.text().indexOf("有道")>=0){
            	return false;
            }
        	/*Document doc=Jsoup.connect("http://hq.sinajs.cn/list=sz002555")
            .timeout(3*1000)
            .proxy(ip, port)
            .get();*/
        	//System.out.println(doc.toString());
            //System.out.println("[" + ip + ":" + port + "] 有效ip...");
            return true;
        } catch (Exception e) {
            //System.out.println("[" + ip + ":" + port + "] 是一个无效ip");
            return false;
        }
    }
}
