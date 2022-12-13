package com.blog.file.server.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.file.client.entity.FileInfo;
import com.blog.common.model.ResultBody;
import com.blog.file.client.entity.vo.FileQueryVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface FileInfoService extends IService<FileInfo> {

    ResultBody ftpUpload(MultipartFile file);

    IPage<FileInfo> getFileInfoQuery(FileQueryVo query);

    ResultBody ftpDownload(String fileId, HttpServletResponse response);
}
