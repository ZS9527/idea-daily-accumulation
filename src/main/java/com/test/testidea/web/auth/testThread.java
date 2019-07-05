package com.test.testidea.web.auth;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.test.testidea.util.FTPClient;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.net.ftp.FTPFile;

/**
 * TODO
 *
 * @author zhangshuai
 * @date 2019/6/14 16:04
 */
public class testThread {

    public static void downloadFileFromFtp(String[] args) throws IOException {
        String ftpHost = "";
        Integer ftpPort = 21;
        String ftpUsername = "";
        String ftpPassword = "";
        FTPClient ftpClient = new FTPClient(ftpHost, Integer.valueOf(ftpPort), ftpUsername, ftpPassword);
        List<FTPFile> ftpFileList = new ArrayList<>();
        ftpFileList = ftpClient.listFileNames("", ftpFileList, true);
        FTPFile ftpFile = ftpFileList.get(0);
        String localFilePath = "";
        if (ftpFile.isFile()) {
            String fileName = ftpFile.getName();
            localFilePath = "D:/finance/" + fileName;
            boolean downloadSuccess = ftpClient.download(ftpFile.getLink() + fileName, localFilePath);
            if (downloadSuccess) {
                System.out.println("下载完成");
            }
        }
        StringBuffer strbuffer = new StringBuffer();
        localFilePath = "D:/finance/20190101.json";
        File file = new File(localFilePath);
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fis, "UTF-8");
            BufferedReader in  = new BufferedReader(inputStreamReader);

            String str;
            while ((str = in.readLine()) != null) {
                strbuffer.append(str);
            }
            in.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
        System.out.println(strbuffer.toString());
        JSONArray jsonArray = JSONArray.parseArray(strbuffer.toString());
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

        }

    }

}
