package com.kuugen.core.quartz;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kuugen.modular.system.model.SysQuartz;
import com.kuugen.modular.system.service.SysQuartzService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用于存放系统启动时执行所有需要执行的定时任务中的线程任务
 *
 * @author yangjiabo
 * @Date  2018-3-2  下午2:38:52
 */
public class QuartzThread implements Runnable {



	private SysQuartzService sysQuartzService;
	/** 日志 */
	private static final Log log = LogFactory.getLog(QuartzThread.class);
	
	 

	public QuartzThread(SysQuartzService sysQuartzService) {
		// TODO Auto-generated constructor stub
		this.sysQuartzService = sysQuartzService;

	}

	@Override
	public void run() {
		 //首先获取任务列表
		QueryWrapper<SysQuartz> wrapper = new QueryWrapper<SysQuartz>();
		wrapper.eq("IS_ACTIVE","1");//查找执行的任务
		List<SysQuartz> quartzs =sysQuartzService.list(wrapper);
		for (SysQuartz po : quartzs) {
			try {
				Map<String, Object> extendeds = new HashMap<String, Object>();
				extendeds.put("sysQuartzService", sysQuartzService);

				
				QuartzUtil.startJob(po.getJobName(), Class.forName(po.getClassName()), po.getPlan(), extendeds);
			} catch (ClassNotFoundException e) {
				log.error("系统重启开始执行任务错误：",e);
			}
		}
	}
}
