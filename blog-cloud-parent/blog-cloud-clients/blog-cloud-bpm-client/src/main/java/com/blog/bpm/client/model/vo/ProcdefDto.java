package com.blog.bpm.client.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@ToString
public class ProcdefDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String proKey;  // 流程标识
    private String procDefId; // 流程定义ID
    private String proName; // 流程名称
    private Integer proVersion; // 流程版本
    private Integer proStatus; // 运行状态 1 运行 2 暂停
    private Integer actCount; // 正在运行的实例数  ===》 有几个这样的流程在跑

    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss")
    private Date deTime;  // 流程部署时间

    private String deId; // 流程部署ID
}
