package com.test.testidea.domain.uploadfile;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 上传文件jpa
 *
 * @author zhangshuai
 * @date 2019/4/17 16:59
 */

public interface FileOperationRepository extends JpaRepository<FileOperation, Serializable> {

}
