package com.hhwy.sync.service;

import com.hhwy.sync.model.Task;

import java.util.List;

public interface IndexService {

    List<Task> getTasks();
    int save();

}
