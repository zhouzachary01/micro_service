package com.hhwy.sync.service.impl;

import com.hhwy.sync.dao.TaskDao;
import com.hhwy.sync.model.Task;
import com.hhwy.sync.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {


    @Autowired
     TaskDao taskDao;



    @Override
    public List<Task> getTasks() {
        List<Task> list = null;
        try {
            list = taskDao.getList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
