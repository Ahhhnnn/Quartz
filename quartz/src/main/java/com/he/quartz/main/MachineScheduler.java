package com.he.quartz.main;

import com.he.quartz.job.MachineJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;


/**
 * 调度器，绑定Trigger，jobDetail，执行job
 */
public class MachineScheduler {
    private static final String JOB_NAME="machinesync_job";

    private static final String JOB_GROUP="defalut";

    private static final String TRIGGER_NAME="machine_trigger";

    private static final String TRIGGER_GROUP="defalut";

    public static void main(String[] args) throws Exception{
        //获取Scheduler工厂
        SchedulerFactory factory=new StdSchedulerFactory();
        //获取Scheduler实例
        Scheduler scheduler=factory.getScheduler();

        //实例化一个JobDetail,命名并分组
        JobDetail jobDetail= JobBuilder.newJob(MachineJob.class).withIdentity(JOB_NAME,JOB_GROUP).build();
        //实例化一个Trigger，命名并分组
        Trigger trigger=TriggerBuilder.newTrigger().withIdentity(TRIGGER_NAME,TRIGGER_GROUP).
                startNow().
                withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")).
                build();
        //讲触发器和任务加入到调度器进行控制
        scheduler.scheduleJob(jobDetail,trigger);
        //启动调度器
        scheduler.start();
    }
}
