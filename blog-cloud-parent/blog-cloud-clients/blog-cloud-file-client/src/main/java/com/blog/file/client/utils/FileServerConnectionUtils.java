package com.blog.file.client.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;

@Slf4j
public class FileServerConnectionUtils {


    /***
     * xhg
     * @param url IP
     * @param port 端口号
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public static FTPClient GetFTPConnection(String url,Integer port,String username,String password)
    {
        FTPClient ftpClient = new FTPClient();
        try {
//            创建连接
            ftpClient.connect(url,port);

            ftpClient.login(username,password);

//            获取FTP 返回的CODE 状态
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)){
                log.error("connect ftp {} failed",url);
                ftpClient.disconnect();
                return null;
            }
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);//设置文件传输模式为二进制，可以保证传输的内容不会被改变
            ftpClient.setControlEncoding("utf-8");//设置ftp字符集
//            被动模式
            ftpClient.enterLocalPassiveMode();
//            设置超时时间30S
            ftpClient.setConnectTimeout(1000*30);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftpClient;
    }
}
