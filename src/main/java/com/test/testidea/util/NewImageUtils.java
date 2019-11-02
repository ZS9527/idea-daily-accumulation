package com.test.testidea.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * TODO
 *
 * @author zhangshuai
 * @date 2019/6/24 15:15
 */
public class NewImageUtils {

    /**
     * 底图经纬度范围
     */
    Double baseMapMinLon = 70.0;
    Double baseMapMaxLon = 140.0;
    Double baseMapMinLat = 0.0;
    Double baseMapMaxLat = 55.0;

//    /**
//     * 底图经纬度范围
//     */
//    Double baseMapMinLon = 73.0;
//    Double baseMapMaxLon = 135.0;
//    Double baseMapMinLat = 3.0;
//    Double baseMapMaxLat = 53.0;

//    /**
//     * 国界图经纬度范围
//     */
//    Double floorMapMinLon = 73.446960;
//    Double floorMapMaxLon = 135.085831;
//    Double floorMapMinLat = 3.408477;
//    Double floorMapMaxLat = 53.557926;

//    /**
//     * 省界图经纬度范围
//     */
//    Double floorMapMinLon = 73.500145;
//    Double floorMapMaxLon = 135.088932;
//    Double floorMapMinLat = 18.158746;
//    Double floorMapMaxLat = 53.561439;


    /**
     * 二层图经纬度范围
     */
    Double floorMapMinLon = 73.746960;
    Double floorMapMaxLon = 135.085831;
    Double floorMapMinLat = 0.508746;
    Double floorMapMaxLat = 53.557926;


    /**
     *
     * @Title: 构造图片
     * @Description: 生成水印并返回java.awt.image.BufferedImage
     * @param filePath
     *            源文件(图片)
     * @param floorFilePath-
     *            水印文件(图片)
     * @param x
     *            距离右下角的X偏移量
     * @param y
     *            距离右下角的Y偏移量
     * @param alpha
     *            透明度, 选择值从0.0~1.0: 完全透明~完全不透明
     * @return BufferedImage
     * @throws IOException
     */
    public  BufferedImage watermark(String filePath, String floorFilePath, int x, int y, float alpha) throws IOException {
        // 获取底图
        BufferedImage baseImg = ImageIO.read(new File(filePath));

        //获取底图宽高度
        int baseImgWidth = baseImg.getWidth();
        int baseImgHeight = baseImg.getHeight();

        x = new Double((baseImgWidth / (baseMapMaxLon - baseMapMinLon)) * (floorMapMinLon - baseMapMinLon)).intValue();
        y = new Double((baseImgHeight / (baseMapMaxLat - baseMapMinLat)) * (floorMapMinLat - baseMapMinLat)).intValue();

        // 获取层图
        BufferedImage floorImg = ImageIO.read(new File(floorFilePath));

        // 创建Graphics2D对象，用在底图对象上绘图
        Graphics2D g2d = baseImg.createGraphics();
        // 获取层图的宽度
        int floorImgWidth = floorImg.getWidth();
        // 获取层图的高度
        int floorImgHeight = floorImg.getHeight();

        //层图压缩的长宽
        int width = floorImgWidth;
        int height = floorImgHeight;

        // 在图形和图像中实现混合和透明效果
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        // 绘制
        g2d.drawImage(floorImg, x, y, floorImgWidth, floorImgHeight, null);
        g2d.dispose();// 释放图形上下文使用的系统资源
        return baseImg;
    }

    /**
     * 按照固定宽高原图压缩
     *
     * @param filePath
     * @param width
     * @param height
     * @throws IOException
     */
    public void thumbnail(String filePath, int width, int height) throws IOException {
        BufferedImage baseImg = ImageIO.read(new File(filePath));
        Image image = baseImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        g.setColor(Color.RED);
        // 绘制处理后的图
        g.drawImage(image, 0, 0, null);
        g.dispose();
        FileOutputStream out = new FileOutputStream(filePath);
        ImageIO.write(tag, "PNG", out);
    }

    /**
     * 输出水印图片
     *
     * @param buffImg
     *            图像加水印之后的BufferedImage对象
     * @param savePath
     *            图像加水印之后的保存路径
     */
    private void generateWaterFile(BufferedImage buffImg, String savePath) {
        int temp = savePath.lastIndexOf(".") + 1;
        try {
            ImageIO.write(buffImg, savePath.substring(temp), new File(savePath));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     *
     * @param args
     * @throws IOException
     *             IO异常直接抛出了
     * @author bls
     */
    public static void main(String[] args) throws IOException {
        String baseFilePath = "D://finance//color.png";
        String firstFloorFilePath = "D://finance//guojie.png";
        String secordBaseFilePath = "D://finance//new.png";

        NewImageUtils newImageUtils = new NewImageUtils();


        newImageUtils.thumbnail(baseFilePath, 1750,1350);
        // 构建叠加层
        BufferedImage buffImg = newImageUtils.watermark(baseFilePath, firstFloorFilePath, 50, 50, 1.0f);
        // 输出水印图片
        newImageUtils.generateWaterFile(buffImg, secordBaseFilePath);

        String secordFloorFilePath = "D://finance//shengjie.png";
        String savePath = "D://finance//save1.png";
        // 构建叠加层
        BufferedImage tempImg = newImageUtils.watermark(secordBaseFilePath, secordFloorFilePath, 50, 50, 1.0f);
        // 输出水印图片
        newImageUtils.generateWaterFile(tempImg, savePath);

    }
}