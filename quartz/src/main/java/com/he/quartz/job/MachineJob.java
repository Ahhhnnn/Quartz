package com.he.quartz.job;

import org.quartz.*;

/**
 * 自定义Job类
 */
public class MachineJob implements Job {

    public MachineJob(){

    }
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobKey key=jobExecutionContext.getJobDetail().getKey();
        JobDataMap dataMap=jobExecutionContext.getJobDetail().getJobDataMap();
        System.out.println(key.getGroup()+"##"+key.getName());
        System.out.println(dataMap.getString("name")+"###"+dataMap.getString("password"));
        System.out.println("machine start up!");
    }
}
