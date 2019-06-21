package com.kuugen.core.quartz;

import com.alibaba.fastjson.JSONArray;

import com.kuugen.core.common.redis.RedisUtil;
import com.kuugen.modular.system.service.SysQuartzService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 用户系统启动时执行所有需要执行的定时任务
 *
 * @author mty
 * @Date  2018-10-29  下午2:35:15
 */
@Component("initQuartz")
public class InitQuartz implements InitializingBean {
	
	@Autowired
	private SysQuartzService sysQuartzService;

	@Autowired
	RedisUtil redisUtil;


	
	@Override
	public void afterPropertiesSet() throws Exception {



		QuartzThread quartzThread = new QuartzThread(sysQuartzService);
		Thread thread = new Thread(quartzThread);
		thread.start();
	}

}