package com.blog.file.server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.file.client.entity.FileInfo;
import com.blog.file.client.entity.vo.FileQueryVo;
import com.blog.file.server.service.FileInfoService;
import com.blog.common.model.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 文件服务控制器
 * </p>
 *
 * @author 
 * @since 2022-11-08
 */
@Slf4j
@Api(value = "文件服务",tags = "文件服务")
@RestController
@RequestMapping("/fileServer")
public class FileInfoController {

    @Autowired
    private FileInfoService fileInfoService;


    /***
     *  通用单文件上传
     * @param file
     * @return
     */
    @ApiOperation(value = "通用单文件上传",notes = "通用单文件上传")
    @PostMapping("/ftpUpload")
    public ResultBody VSFTPDUpload(MultipartFile file)
    {
        ResultBody resultBody = fileInfoService.ftpUpload(file);
        return resultBody;
    }

    /***
     *  通用单文件下载
     * @param fileId
     * @param response
     * @return
     */
    @ApiOperation(value = "通用单文件下载",notes = "通用单文件下载")
    @GetMapping("/ftpDownload")
    public ResultBody VSFTPDDownload(@RequestParam(value = "fileId") String fileId, HttpServletResponse response)
    {
        ResultBody resultBody = fileInfoService.ftpDownload(fileId,response);
        return resultBody;
    }

    /***
     *  通用单文件删除  只删数据 没删文件 假装删一下
     * @param fileId

     * @return
     */
    @ApiOperation(value = "通用单文件删除",notes = "通用单文件删除")
    @DeleteMapping("/deleteById")
    public ResultBody deleteById(@RequestParam(value = "fileId") String fileId)
    {
        QueryWrapper<FileInfo> wrapper = new QueryWrapper<FileInfo>().eq("file_id", fileId);
        boolean result = fileInfoService.remove(wrapper);
        return result==true?ResultBody.ok():ResultBody.fail();
    }

    /***
     *  通用多文件删除  只删数据 没删文件 假装删一下
     * @param fileIds
     * @return
     */
    @ApiOperation(value = "通用多文件删除",notes = "通用多文件删除")
    @PostMapping("/deleteBatch")
    public ResultBody deleteById(@RequestBody String[] fileIds)
    {
        List<String> fileidsBatchh = Arrays.asList(fileIds);
        boolean result = fileInfoService.removeByIds(fileidsBatchh);
        return result==true?ResultBody.ok():ResultBody.fail();
    }



    /***
     *    管理后台 文件信息表  多条件查询文件信息  仅 超级VVIP 可用
     * @return
     */
    @ApiOperation(value = "管理后台多条件查询文件信息",notes = "管理后台多条件查询文件信息")
    @PostMapping("/getFileInfoQuery")
    public ResultBody getUserInfoQuery(@RequestBody FileQueryVo query)
    {
        IPage<FileInfo> pageEntitys = fileInfoService.getFileInfoQuery(query);
        List<FileInfo> records = pageEntitys.getRecords();
        long total = pageEntitys.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("list",records);
        map.put("pageTotal",total);
        return ResultBody.ok().data(map);
    }


}
