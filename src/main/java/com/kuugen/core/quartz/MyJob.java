package com.kuugen.core.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

@DisallowConcurrentExecution
public class MyJob implements Job {

	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		System.out.println(new Date() + ": doing something...");
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
}
