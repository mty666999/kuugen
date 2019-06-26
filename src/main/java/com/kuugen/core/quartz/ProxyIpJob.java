package com.kuugen.core.quartz;

import com.alibaba.fastjson.JSONObject;
import com.kuugen.core.beetl.SpringContextUtil;
import com.kuugen.modular.toolmgr.weather.service.WeatherService;
import com.kuugen.modular.work.ipconfig.mapper.TbWorkIpMapper;
import com.kuugen.modular.work.ipconfig.model.TbWorkIpModel;
import com.kuugen.modular.worm.util.ProxyCralwerUnusedVPN;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * mty
 * 定时获取可用的代理ip
 */
@DisallowConcurrentExecution
public class ProxyIpJob implements Job {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 每次调用就获取5个可用的ip
     * @param context
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.debug("开始获取ip");
        TbWorkIpMapper tbWorkIpMapper = SpringContextUtil.getBean("tbWorkIpMapper");
        ProxyCralwerUnusedVPN proxyCrawler = new ProxyCralwerUnusedVPN();
        String ipstr= proxyCrawler.startCrawler(2);
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

    }
}


