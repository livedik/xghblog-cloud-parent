package com.blog.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;

/***
 * 统一返回结果
 */
@ApiModel("响应结果")
public class ResultBody<T> implements Serializable {

    private static final long serialVersionUID = -6190689122701100762L;

    @ApiModelProperty(value = "响应码:1--请求处理成功")
    private int code = 1;

    @ApiModelProperty(value = "提示信息")
    private String message;

    @ApiModelProperty(value = "返回结果数据")
    private T data;

    @ApiModelProperty(value = "响应时间")
    private LocalDate time = LocalDate.now();

    @ApiModelProperty(value = "附加数据")
    private Map<String,Object> extra;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }


    public static ResultBody ok()
    {
        return new ResultBody().code(1).msg("success!");
    }

    public static ResultBody fail()
    {
        return new ResultBody().code(-1).msg("fail!");
    }

    public ResultBody code(int code)
    {
        this.code = code;
        return this;
    }

    public ResultBody msg(String message)
    {
        this.message = message;
        return this;
    }

    public ResultBody data(T data)
    {
        this.data = data;
        return this;
    }


    public ResultBody put(String key,Object value)
    {
        this.extra.put(key,value);
        return this;
    }


    @Override
    public String toString() {
        return "ResultBody{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", extra=" + extra +
                ", time=" + time +
                '}';
    }
}
