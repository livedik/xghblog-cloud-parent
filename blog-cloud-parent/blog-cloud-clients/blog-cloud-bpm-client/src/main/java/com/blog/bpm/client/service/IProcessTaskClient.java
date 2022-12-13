package com.blog.bpm.client.service;


import com.blog.common.model.ResultBody;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


public interface IProcessTaskClient {


    @PostMapping("/processTask/completeTask")
     ResultBody completeTask(@RequestParam("busKey")String busKey,
                             @RequestParam("message")String message,
                             @RequestParam("result")String result);

    /***
     *
     * @param processCode 流程编码
     * @param busKey   业务ID
     * @return
     */
    @PostMapping("/processTask/startProcess")
    ResultBody startProcess(@RequestParam("processCode")String processCode, @RequestParam("busKey")String busKey, @RequestBody Map map);

    /***
     * 判断业务流程是否完成
     * @param busKey
     * @return
     */
    @GetMapping("/processTask/isComplete")
    boolean isComplete(@RequestParam("busKey")String busKey);

    @GetMapping("/processTask/getProcessByInsId")
    public ResultBody getProcessByInsId(@RequestParam("procInstId") String procInstId);

    /***
     * 获取流程审核记录及业务标识
     * @return
     */
    @ApiOperation(value = "获取流程审核记录及业务标识",notes = "获取流程审核记录及业务标识")
    @GetMapping("/getTaskById")
    public ResultBody getTaskById(@RequestParam("taskId") String taskId);
}

