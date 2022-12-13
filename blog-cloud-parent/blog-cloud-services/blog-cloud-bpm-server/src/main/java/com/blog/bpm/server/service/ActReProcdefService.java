package com.blog.bpm.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.bpm.client.model.entity.ActReProcdef;
import com.blog.bpm.client.model.entity.MyTask;
import com.blog.bpm.client.model.entity.Process;
import com.blog.bpm.client.model.entity.ProcessInst;
import com.blog.bpm.client.model.vo.ProcdefDto;
import com.blog.bpm.client.model.vo.bpmDetailQueryVo;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author
 * @since 2022-11-21
 */
public interface ActReProcdefService extends IService<ActReProcdef> {

    List<ProcdefDto> selectNewPro();

    List<ProcdefDto> selectOldPro();

    List<ProcessInst> getActProcessQuery(bpmDetailQueryVo query);

    List<ProcessInst> getActProcessBydefId(String procDefId);

    List<Process> getProcessByInsId(String procInstId);

    List<MyTask> getOfMyTask(HttpServletRequest request);

    Map<String, Object> getTaskById(String taskId);

}
