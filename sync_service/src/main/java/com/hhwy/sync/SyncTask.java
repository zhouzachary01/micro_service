package com.hhwy.sync;

import com.hhwy.sync.model.Task;
import com.hhwy.sync.service.IndexService;
import com.hhwy.sync.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Component
@Configuration
@EnableScheduling
public class SyncTask implements SchedulingConfigurer {
    protected static Logger logger = LoggerFactory.getLogger(SyncTask.class);

    @Autowired
    private IndexService indexService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        List<Task> taskList = getAllTasks();
        //检查任务配置的是否正确
        checkDataList(taskList);

        int count = 0;
        for(Task task : taskList){
            try {
                scheduledTaskRegistrar.addTriggerTask(getRunnable(task),getTrigger(task));
                count ++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        logger.error("开启定时任务数量为：" + count);

    }



    private Runnable getRunnable(Task task){
        return new Runnable() {
            @Override
            public void run() {
                try {
                    Class cla = Class.forName(task.getClassName());
                    Object obj = SpringUtil.getBean(cla);
                    Method method = obj.getClass().getMethod(task.getMethodName(),null);
                    method.invoke(obj);
                } catch (InvocationTargetException e) {
                    logger.error("定时任务启动错误，反射异常:"+task.getClassName()+";"+task.getMethodName()+";"+ e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }
            }
        };
    }

    private Trigger getTrigger(Task task){
        return new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                //将Cron 0/1 * * * * ? 输入取得下一次执行的时间
                CronTrigger trigger = new CronTrigger(task.getCron());
                Date nextExec = trigger.nextExecutionTime(triggerContext);
                return nextExec;
            }
        };

    }


    private List<Task> getAllTasks() {
        List<Task> list = new ArrayList<>();
        Task task = new Task();
        task.setId("111111");
        task.setClassName("com.ex.ribbon.task.TaskA");
        task.setTaskName("taskA");
        task.setMethodName("run");
        task.setCron("*/5 * * * * ?");


        list = indexService.getTasks();

        return list;
    }

    private List<Task> checkDataList(List<Task> list) {
        String errMsg="";
        for(int i=0;i<list.size();i++){
            if(!checkOneData(list.get(i)).equalsIgnoreCase("success")){
                errMsg+=list.get(i).getTaskName()+";";
                list.remove(list.get(i));
                i--;
            };
        }
        if(!StringUtils.isEmpty(errMsg)){
            errMsg="未启动的任务:"+errMsg;
            logger.error(errMsg);
        }
        return list;
    }

    private String checkOneData(Task task){
        String result="success";
        Class cal= null;
        try {
            cal = Class.forName(task.getClassName());

            Object obj = SpringUtil.getBean(cal);
            Method method = obj.getClass().getMethod(task.getMethodName(),null);
            String cron=task.getCron();
            if(StringUtils.isEmpty(cron)){
                result="定时任务启动错误，无cron:"+task.getTaskName();
            }
        } catch (ClassNotFoundException e) {
            result="定时任务启动错误，找不到类:"+task.getClassName()+ e.getMessage();
            logger.info(result);
        } catch (NoSuchMethodException e) {
            result="定时任务启动错误，找不到方法,方法必须是public:"+task.getClassName()+";"+task.getMethodName()+";"+ e.getMessage();
            logger.info(result);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return result;
    }

}
