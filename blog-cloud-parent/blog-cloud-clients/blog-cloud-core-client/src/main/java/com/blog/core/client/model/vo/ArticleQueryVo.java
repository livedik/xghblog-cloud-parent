package com.blog.core.client.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/***
 * 后台文章列表查询VO
 */
@Getter
@Setter
public class ArticleQueryVo {

    private String title;

    private String author;

    private Integer processStatus;

    private Integer releaseStatus;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date strTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    private Integer pageIndex;

    private Integer pageSize;
}
