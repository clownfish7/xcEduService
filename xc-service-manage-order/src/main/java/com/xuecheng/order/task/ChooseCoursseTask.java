package com.xuecheng.order.task;

import com.xuecheng.framework.domain.task.XcTask;
import com.xuecheng.order.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author yzy
 * @classname ChooseCoursseTask
 * @description TODO
 * @create 2019-08-21 14:57
 */
@Component
@EnableScheduling
public class ChooseCoursseTask {
    @Autowired
    private TaskService taskService;

    @Scheduled(cron = "0/1 * * * * *")
    public void sendChooseCourseTask() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(GregorianCalendar.MINUTE, -1);
        Date time = calendar.getTime();
        List<XcTask> taskList = taskService.findTaskList(time, 1000);
        for (XcTask xcTask : taskList) {
            if (taskService.getTask(xcTask.getId(), xcTask.getVersion()) > 0) {
                taskService.publish(xcTask,xcTask.getMqExchange(),xcTask.getMqRoutingkey() );
            }
        }

    }
}
