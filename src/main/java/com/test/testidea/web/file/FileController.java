package com.test.testidea.web.file;

import com.test.testidea.basic.Result;
import com.test.testidea.param.fileoperation.DownloadFileParam;
import com.test.testidea.param.fileoperation.DownloadZipFileParam;
import com.test.testidea.service.uploadfile.FileOperationService;
import com.test.testidea.vo.file.FileVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件操作类
 *
 * @author zhangshuai
 * @date 2019/4/28 9:58
 */
@Api(tags = "FileOperation")
@Validated
@RestController
@RequestMapping("/fileOperation/")
public class FileController {

    @Autowired
    FileOperationService uploadfileService;

    /**
     * 上传文件
     * @param file
     * @return
     */
    @ApiOperation("上传文件")
    @PostMapping("saveFile")
    public Result<FileVo> saveFile(MultipartFile file){
        return uploadfileService.uploadFile(file);
    }

    /**
     * 下载文件
     * @param param
     * @param response
     * @return
     */
    @ApiOperation("下载文件")
    @GetMapping("downloadFile")
    public void downloadFile(DownloadFileParam param, HttpServletResponse response)
        throws Exception {
        uploadfileService.downloadFile(param, response);
    }

    /**
     * 下载压缩文件
     * @param param
     * @param response
     * @return
     */
    @ApiOperation("下载压缩文件")
    @GetMapping("downloadZipFile")
    public void downloadZipFile(DownloadZipFileParam param, HttpServletResponse response) {
        uploadfileService.downloadZipFile(param, response);
    }

    /**
     * 下载excel文件
     * @param param
     * @param response
     * @return
     */
    @ApiOperation("下载excel文件")
    @GetMapping("downloadExcelFile")
    public void downloadExcelFile(DownloadZipFileParam param, HttpServletResponse response)
        throws Exception {
        uploadfileService.downloadExcelFile(param, response);
    }
}
