package com.blog.bpm.server.mapper;

import com.blog.bpm.client.model.entity.ActReProcdef;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.bpm.client.model.entity.MyTask;
import com.blog.bpm.client.model.entity.Process;
import com.blog.bpm.client.model.entity.ProcessInst;
import com.blog.bpm.client.model.vo.ProcdefDto;
import com.blog.bpm.client.model.vo.bpmDetailQueryVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author
 * @since 2022-11-21
 */
public interface ActReProcdefMapper extends BaseMapper<ActReProcdef> {

    List<ProcdefDto> selectOldPro();

    List<ProcessInst> getActProcessQuery(bpmDetailQueryVo query);

    List<ProcessInst> getActProcessBydefId(String procDefId);

    List<Process> getProcessByInsId(String procInstId);

    List<ProcdefDto> selectNewPro();

    List<MyTask> getOfMyTask(String userName);
}
