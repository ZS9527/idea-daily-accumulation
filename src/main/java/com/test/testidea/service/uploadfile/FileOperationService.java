package com.test.testidea.service.uploadfile;

import com.test.testidea.basic.Result;
import com.test.testidea.param.fileoperation.DownloadFileParam;
import com.test.testidea.param.fileoperation.DownloadZipFileParam;
import com.test.testidea.vo.file.FileVo;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件
 *
 * @author zhangshuai
 * @date 2019/4/17 11:05
 */
public interface FileOperationService {

    /**
     * 上传文件
     * @param uploadFile 文件
     * @return
     */
    Result<FileVo> uploadFile(MultipartFile uploadFile);

    /**
     * 下载文件
     * @param param 文件id
     * @param response
     */
    void downloadFile(DownloadFileParam param, HttpServletResponse response) throws Exception;

    /**
     * 批量下载文件
     * @param param 多个文件id
     * @param response
     */
    void downloadZipFile(DownloadZipFileParam param, HttpServletResponse response);

    /**
     * 下载excel文件
     * @param param
     * @param response
     */
    void downloadExcelFile(DownloadZipFileParam param, HttpServletResponse response)
        throws Exception;
}
