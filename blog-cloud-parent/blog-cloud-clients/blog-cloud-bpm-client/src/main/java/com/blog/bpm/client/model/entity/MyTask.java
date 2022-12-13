package com.blog.bpm.client.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MyTask {

    private String taskId;

    private String taskName;

    private String createName;

    private String procDefId; //流程定义ID

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date  nodeTime;

}
