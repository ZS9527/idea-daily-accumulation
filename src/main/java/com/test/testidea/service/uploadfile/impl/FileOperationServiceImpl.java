package com.test.testidea.service.uploadfile.impl;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.test.testidea.basic.Result;
import com.test.testidea.constant.Static;
import com.test.testidea.domain.uploadfile.FileOperation;
import com.test.testidea.domain.uploadfile.FileOperationRepository;
import com.test.testidea.param.fileoperation.DownloadFileParam;
import com.test.testidea.param.fileoperation.DownloadZipFileParam;
import com.test.testidea.service.uploadfile.FileOperationService;
import com.test.testidea.util.Commons;
import com.test.testidea.util.FileUtil;
import com.test.testidea.vo.file.FileVo;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件实现类
 *
 * @author zhangshuai
 * @date 2019/4/17 11:06
 */

@Service
public class FileOperationServiceImpl implements FileOperationService {

    @Autowired
    FileOperationRepository fileOperationRepository;

    @Override
    public Result<FileVo> uploadFile(MultipartFile uploadFile) {
        FileOperation fileOperation;
        if (uploadFile == null) {
            return Result.no();
        }
        try {
            String localFileName = Commons.uuid() + "." + Files.getFileExtension(uploadFile.getOriginalFilename());
            //将保存路径以 年 / 月 / 日 / 的格式命名
            LocalDateTime nowTime = LocalDateTime.now();
            String realPath = Static.URL + File.separator + nowTime.getYear() + File.separator + nowTime.getMonthValue() + File.separator + nowTime.getDayOfMonth() + File.separator + localFileName;

            FileUtil.saveFile(uploadFile, realPath);
            fileOperation = FileOperation.builder()
                .fileSize(uploadFile.getSize())
                .createTime(LocalDateTime.now())
                .fileName(uploadFile.getOriginalFilename())
                .localFileName(localFileName)
                .saveUrl(realPath.replace(Static.URL, ""))
                .build();
            fileOperation = fileOperationRepository.save(fileOperation);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.no();
        }
        FileVo fileVo = new FileVo();
        fileVo.setData(fileOperation);
        return Result.ok(fileVo);
    }

    @Override
    public void downloadFile(DownloadFileParam param, HttpServletResponse response)
        throws Exception {
        FileOperation fileOperation = fileOperationRepository.findById(param.getFileId()).orElse(null);
        if (fileOperation == null) {
        }
        String realPath = Static.URL + fileOperation.getSaveUrl();
        FileUtil.out(response, new File(realPath), fileOperation.getFileName());
    }

    @Override
    public void downloadZipFile(DownloadZipFileParam param, HttpServletResponse response) {
        String zipName = "myfile.zip";
        List<FileOperation> fileList = fileOperationRepository.findAllById(Lists.newArrayList(param.getFileId()));
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition","attachment; filename="+zipName);

        try {
            ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
            for(Iterator<FileOperation> it = fileList.iterator();it.hasNext();){
                FileOperation file = it.next();
                FileUtil.doZip(new File(Static.URL + file.getSaveUrl()), out, "", file.getFileName());
                response.flushBuffer();
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void downloadExcelFile(DownloadZipFileParam param, HttpServletResponse response)
        throws Exception {

//        List<ExcelVo> excelVoList = new ArrayList<>();
//        ExcelVo excelVo = ExcelVo.builder().id(1L).name("张三").review(3).build();
//        excelVoList.add(excelVo);
//        ExcelUtil excelUtil = new ExcelUtil();
//        File excelFile = excelUtil.export(excelVoList, Excel.XLS);
//        FileUtil.out(response, excelFile, "myFile" + Excel.XLS);
    }
}
