package com.he.quartz.trigger;

import com.he.quartz.dao.UserDao;
import com.he.quartz.job.MachineJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class simpleTrigger {
    private static final String JOB_NAME="machinesync_job";

    private static final String JOB_GROUP="defalut";

    private static final String TRIGGER_NAME="machine_trigger";

    private static final String TRIGGER_GROUP="defalut";

    @Autowired
    private UserDao userDao;

    public static void main(String[] args) throws Exception{

        SchedulerFactory schedulerFactory=new StdSchedulerFactory();

        Scheduler scheduler=schedulerFactory.getScheduler();

        JobDetail job= JobBuilder.newJob(MachineJob.class).withIdentity(JOB_NAME,JOB_GROUP)
                .usingJobData("name","hening")
                .usingJobData("password","123456")
                .build();

        Trigger trigger=TriggerBuilder.newTrigger().withIdentity(TRIGGER_NAME,TRIGGER_GROUP)
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3)
                .withRepeatCount(5))
                .build();
        scheduler.scheduleJob(job,trigger);
        scheduler.start();
    }
}
