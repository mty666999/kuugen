package com.kuugen.modular.worm.work;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.UnknownHostException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * 测试爬虫
 * @author mty
 *
 */
public class TestWorm {

	public static void main(String[] args) throws IOException {
//		获取到可用代理IP	118.190.95.43	9001
//		获取到可用代理IP	61.135.217.7	80
		
		//获取请求连接
        Connection con = Jsoup.connect("http://hq.sinajs.cn/list=sz002555").proxy("118.190.95.43", 9001); 
        //解析请求结果
        Document doc=null;
		try {
			doc = con.ignoreContentType(true).get();  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
//		Document doc=Jsoup.connect("http://hq.sinajs.cn/list=sz002555")
//	            .timeout(3*1000)
//	            .proxy("122.114.31.177", 808)
//	            .get();
		System.out.println(doc.text());
	}
	
	public static InetAddress getInetAddress(){  
		  
        try{  
            return InetAddress.getLocalHost();  
        }catch(UnknownHostException e){  
            System.out.println("unknown host!");  
        }  
        return null;  
  
    }
	public static String getHostName(InetAddress netAddress){  
        if(null == netAddress){  
            return null;  
        }  
        String name = netAddress.getHostName(); //get the host address  
        return name;  
    } 
	 public static String getHostIp(InetAddress netAddress){  
	        if(null == netAddress){  
	            return null;  
	        }  
	        String ip = netAddress.getHostAddress(); //get the ip address  
	        return ip;  
	    }  
}
