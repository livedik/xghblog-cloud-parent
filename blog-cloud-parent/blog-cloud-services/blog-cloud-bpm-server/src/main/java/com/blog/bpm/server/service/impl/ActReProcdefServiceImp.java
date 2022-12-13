package com.blog.bpm.server.service.impl;



import com.blog.bpm.client.model.entity.ActReProcdef;
import com.blog.bpm.client.model.entity.MyTask;
import com.blog.bpm.client.model.entity.Process;
import com.blog.bpm.client.model.entity.ProcessInst;
import com.blog.bpm.client.model.vo.ProcdefDto;
import com.blog.bpm.client.model.vo.bpmDetailQueryVo;
import com.blog.bpm.server.mapper.ActReProcdefMapper;
import com.blog.bpm.server.service.ActReProcdefService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author
 * @since 2022-11-21
 */
@Service
public class ActReProcdefServiceImp extends ServiceImpl<ActReProcdefMapper, ActReProcdef> implements ActReProcdefService {


    @Override
    public List<ProcdefDto> selectNewPro() {
        List<ProcdefDto> procdefNews = this.getBaseMapper().selectNewPro();
        return procdefNews;
    }

    @Override
    public List<ProcdefDto> selectOldPro() {
        List<ProcdefDto> procdefOlds = this.getBaseMapper().selectOldPro();
        return procdefOlds;
    }

    @Override
    public List<ProcessInst> getActProcessQuery(bpmDetailQueryVo query) {
        List<ProcessInst> processInsts = this.getBaseMapper().getActProcessQuery(query);
        return processInsts;
    }

    @Override
    public List<ProcessInst> getActProcessBydefId(String procDefId) {
        List<ProcessInst> processInsts = this.getBaseMapper().getActProcessBydefId(procDefId);
        return processInsts;
    }

    @Override
    public List<Process> getProcessByInsId(String procInstId) {
        List<Process> processList = this.getBaseMapper().getProcessByInsId(procInstId);
        return processList;
    }

    @Override
    public List<MyTask> getOfMyTask(HttpServletRequest request) {
        String userName = "admin";
        List<MyTask> taskList = this.getBaseMapper().getOfMyTask(userName);
        return taskList;
    }


    @Override
    public Map<String, Object> getTaskById(String taskId) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();

        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();
//        流程实例ID
        String proInstId = task.getProcessInstanceId();

        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance instance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(proInstId)
                .singleResult();

//        关联业务ID
        String businessKey = instance.getBusinessKey();

//        获取流程审核记录
        List<Process> processList = this.getProcessByInsId(proInstId);

        Map<String,Object> map = new HashMap<>();
        map.put("key",businessKey);
        map.put("pros",processList);

        return map;
    }
}
