package com.test.testidea.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * FTP使用工具类
 *
 * @author zengwei
 * @date 2018/9/11 9:56
 */

@Data
@Slf4j
public class FTPClient {


    /**
     * IP
     */
    private String host;

    /**
     * 端口号
     */
    private int port;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * FTP服务对象
     */
    private org.apache.commons.net.ftp.FTPClient ftpClient;

    /**
     * 下载文件缓存大小(默认1024)
     */
    private int cache = 1024;


    public FTPClient(String host, int port, String username, String password) throws IOException {
        boolean flag = login(host, port, username, password);
    }

    /**
     * 从FTP服务器上下载文件,支持断点续传，上传百分比汇报
     *
     * @param remotePath 远程文件路径
     * @param localPath 本地文件路径
     * @return 上传的状态
     */
    public boolean download(String remotePath, String localPath) throws IOException {

        // 设置以二进制方式传输
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

        System.out.println("第一次发送心跳检查");

        // 设置被动模式
        ftpClient.enterLocalPassiveMode();

        // 检查远程文件是否存在
        FTPFile[] files = ftpClient.listFiles(new String(remotePath.getBytes("GBK"), "iso-8859-1"));
        if (files.length != 1) {
            return false;
        }
        System.out.println("第二次发送心跳检查");

        long remoteSize = files[0].getSize();
        File file = new File(localPath);
        // 本地存在文件，进行断点下载
        if (file.exists()) {
            long localSize = file.length();
            // 判断本地文件大小是否大于远程文件大小
            if (localSize >= remoteSize) {
                return false;
            }

            // 进行断点续传，并记录状态
            FileOutputStream out = new FileOutputStream(file, true);
            ftpClient.setRestartOffset(localSize);
            return process(remotePath, out, remoteSize, localSize);
        } else {
            long localSize = 0L;
            FileOutputStream out = new FileOutputStream(file);
            return process(remotePath, out, remoteSize, localSize);
        }
    }

    /**
     * 处理文件存储
     *
     * @param remotePath 远程文件路径
     * @param out 本地文件流
     * @param remoteSize 源文件大小
     * @param localSize 本地文件大小
     * @return 状态
     * @throws IOException out报错
     */
    public boolean process(String remotePath, OutputStream out, long remoteSize, long localSize)
        throws IOException {
        InputStream in = ftpClient
            .retrieveFileStream(new String(remotePath.getBytes("GBK"), "iso-8859-1"));
        byte[] bytes;
        if (remoteSize > 0 && remoteSize < cache) {
            bytes = new byte[(int) remoteSize];
        } else {
            bytes = new byte[cache];
        }

        long step = 0, process = 0;
        if (remoteSize > cache) {
            step = remoteSize / 100;
            process = localSize / step;
        }

        int b;
        long tempPosEnd = remoteSize;
        while ((b = in.read(bytes)) != -1) {
            out.write(bytes, 0, b);
            localSize += b;

            if (remoteSize > cache) {
                long nowProcess = localSize / step;
                if (nowProcess > process) {
                    process = nowProcess;
                    if (process % 10 == 0) {
                        log.info("download status......" + process + "%");
                    }
                }
            } else {
                log.info("download status......100%");
            }

            tempPosEnd -= b;
            if (tempPosEnd > 0 && tempPosEnd < cache) {
                bytes = new byte[(int) tempPosEnd];
            } else if (tempPosEnd <= 0) {
                break;
            }
        }
        in.close();
        out.close();
        return ftpClient.completePendingCommand();
    }


    /**
     * 登陆FTP服务器
     *
     * @param host FTPServer IP地址
     * @param port FTPServer 端口
     * @param username FTPServer 登陆用户名
     * @param password FTPServer 登陆密码
     * @return 是否登录成功
     */
    public boolean login(String host, int port, String username, String password)
        throws IOException {
        ftpClient = new org.apache.commons.net.ftp.FTPClient();
        ftpClient.connect(host, port);
        if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
            if (ftpClient.login(username, password)) {
                ftpClient.setControlEncoding("GBK");
                return true;
            }
        }
        return false;
    }

    /**
     * 关闭数据链接
     */
    public void disConnection() throws IOException {
        if (ftpClient.isConnected()) {
            ftpClient.disconnect();
        }
    }

    /**
     * 向FTP连接发送心跳包
     * @throws IOException
     */
    public void heartbeat() throws IOException {
        ftpClient.listHelp();
    }

    /**
     * 递归遍历出目录下面所有文件
     *
     * @param pathName 需要遍历的目录，必须以"/"开始和结束
     * @param ftpFileList 返回结果
     * @param listDirFlag 是否遍历目录下的子目录
     */
    public List<FTPFile> listFileNames(String pathName, List<FTPFile> ftpFileList,
        boolean listDirFlag)
        throws IOException {
        if (null == ftpFileList) {
            ftpFileList = new ArrayList<>();
        }

        String fileSplit = "/";
        if (pathName.startsWith(fileSplit) && pathName.endsWith(fileSplit)) {
            // 更换目录到当前目录
            ftpClient.changeWorkingDirectory(pathName);
            //开启主动模式
            ftpClient.enterLocalPassiveMode();
            FTPFile[] files = ftpClient.listFiles();
            for (FTPFile file : files) {
                file.setLink("");
                if (file.getName().equals(".") || file.getName().equals("..")){
                    continue;
                }
                if(file.isFile()){
                    file.setLink(pathName);
                    ftpFileList.add(file);
                } else if (file.isDirectory()) {
                    if (listDirFlag) {
                        listFileNames(pathName + file.getName() + "/", ftpFileList, listDirFlag);
                    }
                }
            }
        }
        return ftpFileList;
    }

    public List<FTPFile> findNewFileList(String pathName, List<FTPFile> ftpFileList) throws IOException {
        if (null == ftpFileList) {
            ftpFileList = new ArrayList<>();
        }
        String fileSplit = "/";
        if (pathName.startsWith(fileSplit) && pathName.endsWith(fileSplit)) {
            // 更换目录到当前目录
            ftpClient.changeWorkingDirectory(pathName);
            //开启主动模式
            ftpClient.enterLocalPassiveMode();
            FTPFile[] files = ftpClient.listFiles();
            long count = Stream.of(files).filter(ftpFile -> ftpFile.isDirectory()).count();
            if (count > 0) {
                FTPFile dirFile = Stream.of(files)
                    .filter(ftpFile -> ftpFile.isDirectory())
                    .sorted((f1,f2) ->
                        Integer.valueOf(f2.getName().replace("-", "")).compareTo(Integer.valueOf(f1.getName().replace("-", "")))
                    )
                    .collect(Collectors.toList()).get(0);
                findNewFileList(pathName + dirFile.getName() + "/", ftpFileList);
            }
            for (FTPFile file : files) {
                if(file.isFile()){
                    file.setLink(pathName);
                    ftpFileList.add(file);
                }
            }
        }

        return ftpFileList;
    }
}
