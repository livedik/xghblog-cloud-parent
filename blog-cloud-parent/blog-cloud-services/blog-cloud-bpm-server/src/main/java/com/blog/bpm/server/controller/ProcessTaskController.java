package com.blog.bpm.server.controller;

import com.blog.bpm.client.model.entity.ActReProcdef;
import com.blog.bpm.client.model.entity.MyTask;
import com.blog.bpm.client.model.entity.Process;
import com.blog.bpm.client.model.entity.ProcessInst;
import com.blog.bpm.client.model.vo.ProcdefDto;
import com.blog.bpm.client.model.vo.bpmDetailQueryVo;
import com.blog.bpm.server.service.ActReProcdefService;
import com.blog.common.model.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 流程操作 前端控制器
 * </p>
 *
 * @author 
 * @since 2022-11-01
 */
@Slf4j
@Api(value = "流程操作控制器",tags = "流程操作控制器")
@RestController
@RequestMapping("/processTask")
public class ProcessTaskController {

    @Autowired
    private ActReProcdefService actReProcdefService;

    /***
     *
     * @param processCode 流程编码
     * @param busKey   业务ID
     * @return
     */
    @ApiOperation(value = "通用流程启动",notes = "通用流程启动")
    @PostMapping("/startProcess")
    public ResultBody startProcess(
            @RequestParam("processCode")String processCode,
            @RequestParam("busKey")String busKey,
            @RequestBody Map<String,Object> map)
    {
//        获取流程
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        RuntimeService runtimeService = processEngine.getRuntimeService();

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processCode, busKey,map);

        return processInstance==null?ResultBody.fail():ResultBody.ok();

    }


    /***
     * 通用完成流程
     * @param busKey
     * @param message
     * @param result
     * @return
     */
    @ApiOperation(value = "通用完成流程",notes = "通用完成流程")
    @PostMapping("/completeTask")
    public ResultBody completeTask(
            @RequestParam(value = "busKey",required = false)String busKey,
            @RequestParam(value = "message",required = false)String message,
            @RequestParam("result")String result)
    {
//        获取流程
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        TaskService taskService = processEngine.getTaskService();

        Task task = taskService.createTaskQuery()
                .processInstanceBusinessKey(busKey)
                .singleResult();

        Map<String, Object> map = new HashMap<>();
        map.put("result",result);
        //        添加审核意见
        taskService.addComment(task.getId(),task.getProcessInstanceId(),message);
//        完成流程任务
        taskService.complete(task.getId(),map);

        return ResultBody.ok();
    }


    /***
     * 判断业务流程是否完成
     * @param busKey
     * @return
     */
    @ApiOperation(value = "判断业务流程是否完成",notes = "判断业务流程是否完成")
    @GetMapping("/isComplete")
    public boolean isComplete(@RequestParam("busKey")String busKey)
    {
//        获取流程
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        HistoryService historyService = processEngine.getHistoryService();

        HistoricProcessInstance instance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceBusinessKey(busKey)
                .singleResult();

        return instance.getEndActivityId()==null?false:true;
    }


    /***
     * 流程部署
     * @param deName,xmlData,nikeName
     * @return
     */
    @ApiOperation(value = "流程部署",notes = "流程部署")
    @PostMapping("/deployment")
    public ResultBody deployment(@RequestParam("deName")String deName,
                                 @RequestParam("xmlData")String xmlData,
                                 @RequestParam("fileName")String fileName)
    {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = engine.getRepositoryService();
//        前端BPMNJS 默认支持camunda  需要适配 activiti
        String formatXml = xmlData.replace("camunda.org/schema/1.0/bpmn","activiti.org/bpmn")
                .replaceAll("camunda","activiti");

        Deployment deployment = repositoryService.createDeployment()
                .addString(fileName,formatXml)
                .name(deName)
                .deploy();

        // 4、输出部署信息
        System.out.println("流程部署id：" + deployment.getId());
        System.out.println("流程部署名称：" + deployment.getName());

        if (deployment.getName()!="")
        {
            return ResultBody.ok();
        }

        return ResultBody.fail();
    }

    /***
     *  流程部署定义查询    ====》》》 最新版本的  NEW
     * @return
     */
    @GetMapping("/getDeploymentNew")
    public ResultBody getDeploymentNew()
    {
        List<ProcdefDto> procdefList = actReProcdefService.selectNewPro();

        return ResultBody.ok().data(procdefList);
    }


    /***
     *  流程部署定义查询    ====》》》 所有旧版本的   OLD
     * @return
     */
    @GetMapping("/getDeploymentOrd")
    public ResultBody getDeploymentOld()
    {
        List<ProcdefDto> procdefList = actReProcdefService.selectOldPro();
        return ResultBody.ok().data(procdefList);
    }

    /***
     *   多条件查询查询正在运行的流程实例
     * @return
     */
    @PostMapping("/getActProcessQuery")
    public ResultBody getActProcessQuery(@RequestBody bpmDetailQueryVo query)
    {
        log.info("=====================>"+query.getProcDefId());
        List<ProcessInst> processInsts = actReProcdefService.getActProcessQuery(query);
        return ResultBody.ok().data(processInsts);
    }

    /***
     *   查询某类型流程  正在运行的流程实例
     * @return
     */
    @GetMapping("/getActProcessBydefId")
    public ResultBody getActProcessBydefId(@RequestParam("procDefId") String procDefId)
    {
        List<ProcessInst> processInsts = actReProcdefService.getActProcessBydefId(procDefId);
        return ResultBody.ok().data(processInsts);
    }


    /***
     *   查询某流程实例的  审核记录
     * @return
     */
    @GetMapping("/getProcessByInsId")
    public ResultBody getProcessByInsId(@RequestParam("procInstId") String procInstId)
    {
        List<Process> processList = actReProcdefService.getProcessByInsId(procInstId);
        return ResultBody.ok().data(processList);
    }


    /***
     * 获取个人待办的任务
     * @return
     */
    @ApiOperation(value = "获取个人待办的任务",notes = "获取个人待办的任务")
    @PostMapping("/getOfMyTask")
    public ResultBody getOfMyTask(HttpServletRequest request)
    {
        List<MyTask> taskList= actReProcdefService.getOfMyTask(request);
        return ResultBody.ok().data(taskList);
    }



    /***
     * 获取流程审核记录及业务标识
     * @return
     */
    @ApiOperation(value = "获取流程审核记录及业务标识",notes = "获取流程审核记录及业务标识")
    @GetMapping("/getTaskById")
    public ResultBody getTaskById(@RequestParam("taskId") String taskId)
    {
        Map<String,Object> map = actReProcdefService.getTaskById(taskId);
        return ResultBody.ok().data(map);
    }

}
