package com.kuugen.modular.worm.service.impl;

import com.kuugen.modular.work.ipconfig.mapper.TbWorkIpMapper;
import com.kuugen.modular.work.ipconfig.model.TbWorkIpModel;
import com.kuugen.modular.worm.service.WormService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;


 
@Service(value="wormService")
public class WormServiceImpl  implements WormService {

	@Autowired
	private TbWorkIpMapper tbWorkIpMapper;

	@Override
	public Document getHtmlForUrl(String url,int count) {
		// TODO Auto-generated method stub
		if(count>=5){
			return null;
		}
		//此时随机获取一个代理ip
		TbWorkIpModel ip=tbWorkIpMapper.getOne();
		//获取请求连接
        Connection con=null;
		if(ip==null){
			con = Jsoup.connect(url.toString());
		}else{
			con = Jsoup.connect(url.toString()).proxy(ip.getIp_addr(), Integer.parseInt( ip.getPort())); 
		}
		 
        //解析请求结果
        Document doc=null;
        
		try {
			doc = con.ignoreContentType(true).get();  
			//验证是否可用
            if(doc.text().indexOf("无效用户")>=0||doc.text().indexOf("有道")>=0){
//            	TbSysLogModel logm=new TbSysLogModel();
//            	logm.setLog_type("ip");
//            	logm.setMsg(doc.text().substring(0, 200));
//            	logm.setLv("error");
//            	logm.setData_id(ip.getId());
//            	logm.setData_name(ip.getIp_addr());
//            	tbSysLogMapper.insert(logm);
            	tbWorkIpMapper.delete(ip);
            	return getHtmlForUrl(url,count++);
            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			TbSysLogModel logm=new TbSysLogModel();
//        	logm.setLog_type("ip");
//        	logm.setMsg("超时");
//        	logm.setLv("error");
//        	logm.setData_id(ip.getId());
//        	logm.setData_name(ip.getIp_addr());
//        	tbSysLogMapper.insert(logm);
        	tbWorkIpMapper.delete(ip);
        	return getHtmlForUrl(url,count++);
		} 
		return doc;
	}
	
}
