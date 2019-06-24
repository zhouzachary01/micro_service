package com.hhwy.sync.dao;

import com.hhwy.sync.model.Task;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
//@Component
public interface TaskDao {

//    String SQL_GETLIST = "select id id,class_name className,method_name methodName,task_name taskName,cron cron from t_sync_task";
    String SQL_GETLIST = "select id,class_name,method_name,task_name,cron from t_sync_task";
    String SQL_INSERT = "insert into t_sync_task (id,class_name,method_name,task_name,cron) values ((#{id},#{className},#{methodName},#{taskName},#{cron})";
    String SQL_UPDATE = "update t_sync_task set class_name=#{className},method_name=#{methodName},task_name=#{taskName},cron=#{cron} where id=#{id}";
    @Select(SQL_GETLIST)
    List<Task> getList();

    @Insert(SQL_INSERT)
    int save(Task task);

    @Update(SQL_UPDATE)
    int update(Task task);
}
