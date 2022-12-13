package com.blog.file.client.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 文件信息表
 * </p>
 *
 * @author 
 * @since 2022-11-09
 */
@Getter
@Setter
@TableName("file_info")
@ApiModel(value = "FileInfo对象", description = "文件信息表")
public class FileInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文件ID")
    @TableId(type = IdType.ASSIGN_ID)
    private String fileId;

    @ApiModelProperty("关联用户ID")
    private String reUid;

    @ApiModelProperty("文件原始名称")
    private String fileName;

    @ApiModelProperty("文件访问路径")
    private String fileUrl;

    @ApiModelProperty("文件后缀")
    private String fileSuffix;

    @ApiModelProperty("上传人名称")
    private String createName;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date gmtCreate;

    @ApiModelProperty("是否删除 1：是 0： 否")
    @TableLogic
    private Integer isDel;
}