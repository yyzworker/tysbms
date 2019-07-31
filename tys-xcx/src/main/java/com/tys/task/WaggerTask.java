package com.tys.task;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.Date;
@Configuration
@EnableScheduling
public class WaggerTask {


    @Scheduled(cron ="0 0 21 * * ?") //晚上21：00执行操作
    private void executeWaggerResult(){
        System.out.println("晚上21：00执行操作"+new Date());
    }

    @Scheduled(cron ="0 0 13 * * ?") //晚上13：00执行操作
    private void executeAddTask(){
        //不允许进行上一个投注任务

        //产生新的投注任务
        System.out.println("下午13：00执行操作"+new Date());
    }
}
