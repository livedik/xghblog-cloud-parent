package com.blog.core.client.model.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.blog.common.mybatis.entity.AbstractEasyFieldEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2022-11-16
 */
@Getter
@Setter
@ApiModel(value = "Article对象", description = "文章信息")
public class Article extends AbstractEasyFieldEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文章ID")
    @TableId(type = IdType.INPUT)
    private String articleId;

    @ApiModelProperty("文章标题")
    private String title;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("审核状态")
    private Integer processStatus;

    @ApiModelProperty("发布状态")
    private Integer releaseStatus;

    @ApiModelProperty("文本内容")
    private String content;

    @ApiModelProperty("文本内容加了样式的")
    private String contentHtml;

    @TableLogic
    private Integer isDel;
}
