package com.he.quartz.controller;

import com.he.quartz.dao.UserDao;
import com.he.quartz.job.MachineJob;
import com.he.quartz.model.User;
import org.apache.ibatis.jdbc.SqlRunner;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final String JOB_NAME="machinesync_job";

    private static final String JOB_GROUP="defalut";

    private static final String TRIGGER_NAME="machine_trigger";

    private static final String TRIGGER_GROUP="defalut";
    @Autowired
    private UserDao userDao;

    @Autowired
    private DataSource dataSource;

    @RequestMapping("/insert")
    public String insert() throws Exception{
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
        return "success";
    }

    @RequestMapping("/update")
    public String update(){
        Connection conn=null;
        try {
           conn=dataSource.getConnection();
           SqlRunner sqlRunner = new SqlRunner(conn);
           String sql="update tb_user set name=?,password = ? where id = ?";
           sqlRunner.update(sql,"hehehe","11111",1);
           sqlRunner.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return "success";
    }

    @RequestMapping("delete")
    public String delete(){
        Connection conn=null;
        try {
            conn=dataSource.getConnection();
            SqlRunner sqlRunner = new SqlRunner(conn);
            String sql="delete from tb_user where id = ?";
            sqlRunner.update(sql,2);
            sqlRunner.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return "delete success";
    }
}
