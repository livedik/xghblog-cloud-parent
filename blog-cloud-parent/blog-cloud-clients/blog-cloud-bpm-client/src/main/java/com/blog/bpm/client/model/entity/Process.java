package com.blog.bpm.client.model.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class Process implements Serializable {

    private static final long serialVersionUID = 1L;

    private String procDefId;

    private String procInstId;  // 流程ID

    private String nodeName; //节点名称

    private String assignee;  // 节点审核人

    private String nodeType; // 节点类型

    private String message; // 审核意见

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm")
    private Date strTime;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm")
    private Date endTime;



}
