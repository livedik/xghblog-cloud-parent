package com.blog.bpm.server;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.blog.bpm.client.model.entity.ActReProcdef;
import com.blog.bpm.client.model.vo.ProcdefDto;
import com.blog.bpm.server.service.ActReProcdefService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProceTest {

//    @Autowired
//    RuntimeService runtimeService;



    @Autowired
    private ActReProcdefService actReProcdefService;


    @Test
    public void test01()
    {

//        repositoryService.createDeployment()
//                .addClasspathResource("/resources/bpmn/diagram.png")
//                .addClasspathResource("/resources/bpmn/test.bpmn20.xml")
//                .name("出差申请")
//                .deploy();

        // 1、创建ProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
//                .addClasspathResource("bpmn/diagram.bpmn20.xml")
                .addClasspathResource("bpmn/articleSubmit.bpmn20.xml")
                .addClasspathResource("bpmn/diagram.png")
                .name("博客文章审核流程")
                .deploy();

        // 4、输出部署信息
        System.out.println("流程部署id：" + deployment.getId());
        System.out.println("流程部署名称：" + deployment.getName());

    }


//    启动流程
    @Test
    public void test02()
    {

//        repositoryService.createDeployment()
//                .addClasspathResource("/resources/bpmn/diagram.png")
//                .addClasspathResource("/resources/bpmn/test.bpmn20.xml")
//                .name("出差申请")
//                .deploy();

        // 1、创建ProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String, Object> map = new HashMap<>();

        map.put("username","人才");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("articleSubmit",map);

// 输出内容
        System.out.println("流程定义id：" + processInstance.getProcessDefinitionId());
        System.out.println("流程实例id：" + processInstance.getId());
        System.out.println("当前活动Id：" + processInstance.getActivityId());

//        test03();

    }


    @Test
    public void test03()
    {

//        repositoryService.createDeployment()
//                .addClasspathResource("/resources/bpmn/diagram.png")
//                .addClasspathResource("/resources/bpmn/test.bpmn20.xml")
//                .name("出差申请")
//                .deploy();

        // 1、创建ProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        TaskService taskService = processEngine.getTaskService();
//
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("articleSubmit")
                .taskAssignee("theSky")
                .processInstanceBusinessKey("72d95c48e07d4870bcb5e96c1413f64d")
//                .list();
                .singleResult();

//        Task task = taskService.createTaskQuery()
//                .taskId("112507")
//                .singleResult();

//        for (Task task : list) {
//            System.out.println("流程实例id：" + task.getProcessInstanceId());
//            System.out.println("任务id：" + task.getId());
//            System.out.println("任务负责人：" + task.getAssignee());
//            System.out.println("任务名称：" + task.getName());
//        }
        Map<String, Object> map = new HashMap<>();
//
//        map.put("result","pass");
        taskService.addComment(task.getId(),task.getProcessInstanceId(),"fuck");
        taskService.complete(task.getId(),map);


    }


    @Test
    public void test04()
    {
//        repositoryService.createDeployment()
//                .addClasspathResource("/resources/bpmn/diagram.png")
//                .addClasspathResource("/resources/bpmn/test.bpmn20.xml")
//                .name("出差申请")
//                .deploy();

        // 1、创建ProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("test")
                .orderByProcessDefinitionVersion()
                .desc()
                .list();


        // 输出流程定义信息
        for (ProcessDefinition processDefinition : list) {
            System.out.println("流程定义 id="+processDefinition.getId());
            System.out.println("流程定义 name="+processDefinition.getName());
            System.out.println("流程定义 key="+processDefinition.getKey());
            System.out.println("流程定义 Version="+processDefinition.getVersion());
            System.out.println("流程部署ID ="+processDefinition.getDeploymentId());
        }


    }

    @Test
    public void test05(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();

        repositoryService.deleteDeployment("30001");

    }

    @Test
    public void test06(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = defaultProcessEngine.getRuntimeService();
        List<ProcessInstance> instances = runtimeService.createProcessInstanceQuery()
                .processDefinitionKey("articleSubmit")
                .list();


        for (ProcessInstance processInstance : instances) {
            System.out.println("----------------------------");
            System.out.println("流程实例id："
                    + processInstance.getProcessInstanceId());
            System.out.println("所属流程定义id："
                    + processInstance.getProcessDefinitionId());
            System.out.println("是否执行完成：" + processInstance.isEnded());
            System.out.println("是否暂停：" + processInstance.isSuspended());
            System.out.println("当前活动标识：" + processInstance.getActivityId());
        }

    }

    @Test
    public void test07(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();

        HistoryService historyService = defaultProcessEngine.getHistoryService();
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey("d604256e895b419c91ce60986b4e458b")
                .singleResult();

        System.out.println(historicProcessInstance.getEndActivityId());
    }


    @Test
    public void test08(){
        List<ProcdefDto> procdefDtos = actReProcdefService.selectNewPro();
        procdefDtos.stream()
                        .filter((p) -> p.getProVersion()>3)
                                .forEach(System.out::println);
    }
}
