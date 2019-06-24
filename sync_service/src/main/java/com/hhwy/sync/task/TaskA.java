package com.hhwy.sync.task;

import com.hhwy.sync.dao.TaskDao;
import com.hhwy.sync.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskA {

    @Autowired
    private TaskDao taskDao;


    public void run(){
        List<Task> list = taskDao.getList();
        System.out.println(list.size());
        System.out.println("taskA:run方法开始。。。");
    }
}
