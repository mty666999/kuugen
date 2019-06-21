package com.kuugen.core.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 定时任务动态添加、修改、删除工具类
 *
 * @author yangjiabo
 * @Date  2018-3-2  上午9:56:07
 */
public class QuartzUtil {

	private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
	
	/**
	* 执行并添加一个定时任务 
     *  
     * @param jobName 任务名 
     * @param jobClass  任务类
     * @param cron   cron表达式    时间设置，参考quartz说明文档
	 * @param extendeds   扩展的对象，即需要在job类中使用的对象
	 */
    @SuppressWarnings({ "unchecked", "rawtypes" })  
    public static void startJob(String jobName, Class jobClass, String cron, Map<String,Object> extendeds) {  
    	//任务组名
    	String jobGroupName = "XLXXCC_JOB_GROUP";
    	//触发器名
    	String triggerName = "trigger_"+jobName;
    	//触发器组名
    	String triggerGroupName = jobGroupName;
    	try {  
            Scheduler sched = schedulerFactory.getScheduler();  
            // 任务名，任务组，任务执行类
            JobDetail jobDetail= JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();

            JobDataMap jobDateMap = jobDetail.getJobDataMap();
            Set<Entry<String,Object>> entrySet = extendeds.entrySet();
            for (Entry<String,Object> e : entrySet) {
				jobDateMap.put(e.getKey(), e.getValue());
			}
            
            // 触发器  
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组  
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            triggerBuilder.startNow();
            // 触发器时间设定  
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            // 创建Trigger对象
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();

            // 调度容器设置JobDetail和Trigger
            sched.scheduleJob(jobDetail, trigger);  

            // 启动  
            if (!sched.isShutdown()) {  
                sched.start();  
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }
    
    /** 
     * 移除一个任务 
     *  
     * @param jobName   任务名
     */  
    public static void removeJob(String jobName) {
    	//任务组名
    	String jobGroupName = "XLXXCC_JOB_GROUP";
    	//触发器名
    	String triggerName = "trigger_"+jobName;
    	//触发器组名
    	String triggerGroupName = jobGroupName;
        try {  
            Scheduler sched = schedulerFactory.getScheduler();  

            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

            sched.pauseTrigger(triggerKey);// 停止触发器  
            sched.unscheduleJob(triggerKey);// 移除触发器  
            sched.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }
}
