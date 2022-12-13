package com.blog.bpm.client.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 流程详情查询VO
 * </p>
 *
 * @author 
 * @since 2022-11-09
 */
@Getter
@Setter
@ApiModel(value = "流程详情查询VO", description = "流程详情查询VO")
public class bpmDetailQueryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String procDefId;  // 流程定义ID
}