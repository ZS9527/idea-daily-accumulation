package com.test.testidea.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件处理工具类
 * @author fangzhimin
 * @date 2018/8/17 10:00
 */

@Slf4j
public class FileUtil {

    public static final int CACHE_SIZE = 1024;

    /**
     * 删除文件/文件夹
     *
     * @param path 文件/文件夹路径
     * @throws IOException
     */
    public static void delete(String path) throws IOException {
        Path start = Paths.get(path);

        if (Files.notExists(start)) {
            throw new IOException("文件/文件夹不存在！");
        }

        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException e)
                    throws IOException {
                if (e == null) {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                } else {
                    throw e;
                }
            }

        });
    }

    /**
     * 保存图片
     * @param multipartFile   上传的图片
     * @return 图片保存相对路径
     */
    public static void saveFile(MultipartFile multipartFile, String realPath) throws IOException {

        if(null == multipartFile) {
            throw new IOException();
        }

        File file = new File(realPath);
        if(!file.getParentFile().exists()) {
            boolean mkFlag = file.getParentFile().mkdirs();
            if (!mkFlag) {
                throw new RuntimeException();
            }
        }

        multipartFile.transferTo(file);

        return;
    }

    public static void out(HttpServletResponse response, File file, String outname) throws Exception {
        output(response, file, outname);
    }

    public static void out(HttpServletResponse response, File file) throws Exception {
        output(response, file, file.getName());
    }

    /**
     * 输出文件
     * @param response
     * @param file
     * @throws Exception
     */
    private static void output(HttpServletResponse response, File file, String name) throws Exception {
        response.setHeader("conent-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder
            .encode(name, "UTF-8"));
        OutputStream os = response.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
        InputStream is = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(is);
        byte[] temp = new byte[10 * 1024];
        int len = 0;
        while ((len = bis.read(temp)) != -1) {
            bos.write(temp, 0, len);
        }
        bos.flush();
        bis.close();
        bos.close();
        is.close();
    }

    /**
     * 拷贝资源文件
     * @param name
     * @param out
     */
    public static void copyResource(String name, File out) {
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            is = FileUtil.class.getResourceAsStream("/" + name);
            fos = new FileOutputStream(out);
            byte[] b = new byte[1024];
            while ((is.read(b)) != -1) {
                fos.write(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 压缩文件
     * @param srcFile 被压缩文件路径或者单个文件
     * @param zipFile 压缩文件路径
     * @throws IOException
     */
    public static void doCompress(String srcFile, String zipFile) throws IOException {
        doCompress(new File(srcFile), new File(zipFile));
    }

    /**
     * 文件压缩
     * @param srcFile 目录或者单个文件
     * @param zipFile 压缩后的ZIP文件
     */
    public static void doCompress(File srcFile, File zipFile) throws IOException {
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(zipFile));
            doCompress(srcFile, out);
        } catch (Exception e) {
            throw e;
        } finally {
            out.close();//记得关闭资源
        }
    }

    /**
     * 压缩文件
     * @param filelName 被压缩文件路径或单个文件路径
     * @param out 压缩文件流
     * @throws IOException
     */
    public static void doCompress(String filelName, ZipOutputStream out) throws IOException{
        doCompress(new File(filelName), out);
    }

    /**
     * 压缩文件
     * @param file 被压缩文件
     * @param out 压缩文件流
     * @throws IOException
     */
    public static void doCompress(File file, ZipOutputStream out) throws IOException{
        doCompress(file, out, "");
    }

    /**
     * 压缩文件
     * @param inFile 被压缩文件
     * @param out 压缩文件流
     * @param dir 压缩文件路径
     * @throws IOException
     */
    public static void doCompress(File inFile, ZipOutputStream out, String dir) throws IOException {
        if ( inFile.isDirectory() ) {
            File[] files = inFile.listFiles();
            if (files!=null && files.length>0) {
                for (File file : files) {
                    String name = inFile.getName();
                    if (!"".equals(dir)) {
                        name = dir + "/" + name;
                    }
                    FileUtil.doCompress(file, out, name);
                }
            }
        } else {
            FileUtil.doZip(inFile, out, dir);
        }
    }

    /**
     * 压缩文件
     * @param inFile 被压缩文件
     * @param out 压缩文件流
     * @param dir 压缩文件保存路径
     * @throws IOException
     */
    public static void doZip(File inFile, ZipOutputStream out, String dir) throws IOException {
        String entryName = null;
        if (!"".equals(dir)) {
            entryName = dir + "/" + inFile.getName();
        } else {
            entryName = inFile.getName();
        }
        ZipEntry entry = new ZipEntry(entryName);
        out.putNextEntry(entry);

        int len = 0 ;
        byte[] buffer = new byte[1024];
        FileInputStream fis = new FileInputStream(inFile);
        while ((len = fis.read(buffer)) > 0) {
            out.write(buffer, 0, len);
            out.flush();
        }
        out.closeEntry();
        fis.close();
    }

    public static void main(String[] args) throws IOException {
        doCompress("D:/java/", "D:/java.zip");
    }


}
