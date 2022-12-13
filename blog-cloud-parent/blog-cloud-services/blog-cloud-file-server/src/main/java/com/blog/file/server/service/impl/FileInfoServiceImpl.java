package com.blog.file.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.file.client.entity.FileInfo;
import com.blog.file.client.entity.vo.FileQueryVo;
import com.blog.file.client.utils.FileServerConnectionUtils;
import com.blog.file.server.mapper.FileInfoMapper;
import com.blog.file.server.service.FileInfoService;
import com.blog.common.model.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


@Service
@Slf4j
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements FileInfoService {

    @Value("${vsftpd.username}")
    private String vsUsername;

    @Value("${vsftpd.password}")
    private String vsPassword;

    @Value("${vsftpd.port}")
    private Integer vsPort;

    @Value("${vsftpd.host}")
    private String vsUrl;

    private String ftpDURL = "http://43.138.253.95:24588";


    @Override
    public ResultBody ftpUpload(MultipartFile file) {
//        获取FTP Client
        FTPClient ftpClient = FileServerConnectionUtils.GetFTPConnection(vsUrl, vsPort, vsUsername, vsPassword);

//        当前年月日作为存储路径
        String filePath = new SimpleDateFormat("/yyyy/MM/dd/").format(new Date());

        String uuid = UUID.randomUUID().toString().replaceAll("-","");

        try {
            //        获取文件名
            String filename = file.getOriginalFilename().split("\\.")[0];

//            文件后缀
            String suffix = file.getOriginalFilename().split("\\.")[1];
            //        获取文件流
            InputStream is = file.getInputStream();
//            判断是否存在改路径  有就直接存 没有就Create
            if (ftpClient.changeWorkingDirectory(filePath))
            {

                boolean uploadResult = ftpClient.storeFile(new String((uuid+"."+suffix).getBytes(StandardCharsets.UTF_8), "ISO-8859-1"), is);
                if (uploadResult)
                {
                    log.info(filename+"文件上传成功！");
                    //                        文件上传成功 保存上传信息
                    FileInfo fileInfo = new FileInfo();
//                        文件名称
                    fileInfo.setFileName(filename);
//                        文件后缀
                    fileInfo.setFileSuffix(suffix);
                    fileInfo.setCreateName("admin");
                    fileInfo.setGmtCreate(new Date());
                    fileInfo.setReUid("1");
                    fileInfo.setFileUrl(ftpDURL+filePath+uuid+"."+suffix);
                    this.getBaseMapper().insert(fileInfo);

                    return ResultBody.ok();
                }
                else
                {
                    log.info(filename+"文件上传失败！");
                    return ResultBody.fail();
                }
            }
            else
            {
                String[] dirs = filePath.split("/");
                String tempDir = "";
                for (String dir : dirs) {
                    if ("".equals(dir))continue;
                    tempDir += "/" + dir;
                    if (!ftpClient.changeWorkingDirectory(tempDir))
                    {
                        ftpClient.makeDirectory(tempDir);
                    }

                }
                if (ftpClient.changeWorkingDirectory(filePath))
                {
                    boolean uploadResult = ftpClient.storeFile(new String((uuid+"."+suffix).getBytes(StandardCharsets.UTF_8), "ISO-8859-1"), is);
                    if (uploadResult)
                    {
                        log.info(filename+"文件上传成功！");

//                        文件上传成功 保存上传信息
                        FileInfo fileInfo = new FileInfo();
//                        文件名称
                        fileInfo.setFileName(filename);
//                        文件后缀
                        fileInfo.setFileSuffix(suffix);
                        fileInfo.setCreateName("admin");
                        fileInfo.setGmtCreate(new Date());
                        fileInfo.setReUid("1");
                        fileInfo.setFileUrl(ftpDURL+filePath+uuid+"."+suffix);
                        this.getBaseMapper().insert(fileInfo);

                        return ResultBody.ok();
                    }else
                    {
                        log.info(filename+"文件上传失败！");
                        return ResultBody.fail();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResultBody.fail();
    }

    @Override
    public IPage<FileInfo> getFileInfoQuery(FileQueryVo query) {
        String fileName = query.getFileName(); //文件名
        String createName = query.getCreateName(); // 创建人
        String fileSuffix = query.getFileSuffix(); //文件类型
        Date strTime = query.getStrTime()==null?new Date():query.getStrTime(); //上传开始时间
        Date endTime = query.getEndTime()==null?new Date():query.getEndTime(); //上传截止时间

        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();

        if (!"".equals(fileName))queryWrapper.like("file_name",fileName);
        if (!"".equals(createName))queryWrapper.like("create_name",createName);
        if (!"".equals(fileSuffix))queryWrapper.eq("file_suffix",fileSuffix);
        if (strTime!=null) queryWrapper.between("gmt_create",strTime,endTime);

        Page<FileInfo> page = new Page<>();
        page.setSize(query.getPageSize());
        page.setCurrent(query.getPageIndex());

        IPage<FileInfo> fileInfoIPage = this.getBaseMapper().selectPage(page, queryWrapper);
        return fileInfoIPage;
    }

    @Override
    public ResultBody ftpDownload(String fileId,HttpServletResponse response) {

        FileInfo fileInfo = this.getOne(new QueryWrapper<FileInfo>().select("file_url").eq("file_id",fileId));
        if (fileInfo==null)return ResultBody.fail().msg("找不到该文件！可能文件数据已被清空");
        String fileUrl = fileInfo.getFileUrl();
        //        获取FTP Client
        FTPClient ftpClient = FileServerConnectionUtils.GetFTPConnection(vsUrl, vsPort, vsUsername, vsPassword);
//        http://43.138.253.95:24588/2022/11/09/2fd75ac1f7054e1984685984b1f9ff56.jpg
        String notUrl = fileUrl.replaceFirst(ftpDURL, "");

        String[] fileUrls = fileUrl.split("/");
        String fileName = fileUrls[fileUrls.length-1];
        String filePath = notUrl.replaceFirst(fileName, "");

        InputStream is = null;
        ServletOutputStream os =null;
        try {
            boolean b = ftpClient.changeWorkingDirectory(filePath);
            is = ftpClient.retrieveFileStream(fileName);
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName.substring(10));

            os = response.getOutputStream();

            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len=is.read(buffer))!=-1)
            {
                os.write(buffer,0,len);
            }
            is.close();
            os.flush();
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;
    }
}
