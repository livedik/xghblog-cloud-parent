package com.blog.bpm.client.model.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class ProcessInst implements Serializable {

    private static final long serialVersionUID = 1L;

    private String executionId;  // 执行ID
    private String procInstId;  // 流程实例ID
    private String procDefId;  // 流程定义ID
    private String proName;  // 流程名称
    private String description; // 描述
    private String assignee; // 节点审核人
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss")
    private Date nodeStrTime; // 节点开始时间
    private Integer proStatus;  // 节点状态
    private String createName; // 流程申请人
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss")
    private Date strTime; // 流程创建时间

}
