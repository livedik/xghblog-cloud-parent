package com.blog.bpm.client.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2022-11-21
 */
@Getter
@Setter
@TableName("act_re_procdef")
@ApiModel(value = "ActReProcdef对象", description = "")
public class ActReProcdef implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private Integer rev;

    private String category;

    private String name;

    private String key;

    private Integer version;

    private String deploymentId;

    private String resourceName;

    private String dgrmResourceName;

    private String description;

    private Byte hasStartFormKey;

    private Byte hasGraphicalNotation;

    private Integer suspensionState;

    private String tenantId;

    private String engineVersion;
}
