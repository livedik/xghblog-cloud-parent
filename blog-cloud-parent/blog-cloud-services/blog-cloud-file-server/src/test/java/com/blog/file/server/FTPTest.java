package com.blog.file.server;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class FTPTest {


    @Test
    public void uploadTest()
    {
        FTPClient ftpClient = connectFtpServer();

//        文件保存目录
        try {
            File file = new File("D:\\c.txt");
            FileInputStream is = new FileInputStream(file);

            boolean b = ftpClient.changeWorkingDirectory("/");
            boolean wahaha = ftpClient.storeFile(new String("haha22.txt".getBytes(StandardCharsets.UTF_8),"ISO-8859-1"), is);

            is.close();
            ftpClient.logout();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }


    private FTPClient connectFtpServer(){
        FTPClient ftpClient = new FTPClient();
//        ftpClient.setConnectTimeout(1000*30);//设置连接超时时间

//        ftpClient.enterLocalPassiveMode();//设置被动模式，文件传输端口设置
        try {

            ftpClient.connect("43.138.253.95",21);
            boolean login = ftpClient.login("ftpuser", "qw520224");
            System.out.println(login);

            int replyCode = ftpClient.getReplyCode();
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);//设置文件传输模式为二进制，可以保证传输的内容不会被改变
            ftpClient.setControlEncoding("utf-8");//设置ftp字符集
            ftpClient.enterLocalPassiveMode();
            ftpClient.setConnectTimeout(3000000);
            if (!FTPReply.isPositiveCompletion(replyCode)){
                log.error("connect ftp {} failed","43.138.253.95");
                ftpClient.disconnect();
                return null;
            }
            log.info("replyCode==========={}",replyCode);
        } catch (IOException e) {
            log.error("connect fail ------->>>{}",e.getCause());
            return null;
        }
        return ftpClient;
    }

}
