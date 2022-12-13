package com.blog.file.client.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/***
 * 后台文件上传查询VO
 */
@Getter
@Setter
public class FileQueryVo {

    private String fileName;

    private String createName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date strTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    private String fileSuffix;

    private Integer pageSize;

    private Integer pageIndex;
}
