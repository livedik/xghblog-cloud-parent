package com.blog.base.client.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.blog.common.mybatis.entity.AbstractEasyFieldEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 系统路由列表
 * </p>
 *
 * @author 
 * @since 2022-10-18
 */
@Getter
@Setter
@TableName("route_gateway")
@ApiModel(value = "RouteGateway对象", description = "系统路由列表")
public class RouteGateway extends AbstractEasyFieldEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("路由ID")
    private Long routeId;

    @ApiModelProperty("路由名称")
    private String routeName;

    @ApiModelProperty("路由路径")
    private String routePath;

    @ApiModelProperty("服务ID")
    private String serviceId;

    @ApiModelProperty("是否忽略前缀 0 ：是  1：否")
    private Integer stripPrefix;

    @ApiModelProperty("是否重试 0 ：是  1：否")
    private Integer retryable;

    @ApiModelProperty("是否有效 0 ：是  1：否")
    private Integer status;

    @ApiModelProperty("是否保留数据 0 ：是  1：否")
    private Integer isPersist;

    @ApiModelProperty("路由说明")
    private String routeDesc;

}
