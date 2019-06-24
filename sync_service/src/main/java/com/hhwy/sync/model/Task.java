package com.hhwy.sync.model;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "t_sync_task")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity(name = "Task")
public class Task implements Serializable {

    @Id
    @Column(name = "id",columnDefinition = "varchar(50) comment '主键id'")
    private String id;
    @Column(name = "class_name",columnDefinition = "varchar(255) comment 'class名称'")
    private String className;
    @Column(name = "method_name",columnDefinition = "varchar(255) comment '方法名称'")
    private String methodName;
    @Column(name = "cron",columnDefinition = "varchar(255) comment '定时表达式'")
    private String cron;
    @Column(name = "task_name",columnDefinition = "varchar(255) comment '定时任务描述'")
    private String taskName;


}
