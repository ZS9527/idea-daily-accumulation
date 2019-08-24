package com.test.testidea.web.mapbox;

import com.test.testidea.util.FileUtil;
import io.swagger.annotations.ApiOperation;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 抓取网站上的瓦片
 *
 * @author zhangshuai
 * @date 2019/8/6 17:41
 */

@RestController
@RequestMapping("/MapboxPdf/")
public class MapboxPdf {

    public static void main(String[] args) {

        for (int z = 13; z <= 16; z++) {
            switch (z) {
                case 11:
                    for (int x = 1627; x <= 1688 ; x++) {
                        System.out.println("11" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        for (int y = 834; y <= 889; y++) {
                            downWapian(z, x, y);
                        }
                    }
                    break;
                case 12 :
                    for (int x = 3255; x <= 3374 ; x++) {
                        System.out.println("12" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        for (int y = 1670; y <= 1779; y++) {
                            downWapian(z, x, y);
                        }
                    }
                    break;
                case 13 :
                    for (int x = 6510; x <= 6753 ; x++) {
                        System.out.println("13" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        for (int y = 3336; y <= 3358; y++) {
                            downWapian(z, x, y);
                        }
                    }
                    break;
                case 14 :
                    for (int x = 13020; x <= 13507 ; x++) {
                        System.out.println("14" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        for (int y = 6673; y <= 7117; y++) {
                            downWapian(z, x, y);
                        }
                    }
                    break;
                case 15 :
                    for (int x = 26041; x <= 27014 ; x++) {
                        System.out.println("15" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        for (int y = 13347; y <= 14235; y++) {
                            downWapian(z, x, y);
                        }
                    }
                    break;
                case 16 :
                    for (int x = 52088; x <= 54028 ; x++) {
                        System.out.println("16" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        for (int y = 26701; y <= 28470; y++) {
                            downWapian(z, x, y);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @ApiOperation("添加地图瓦片")
    @GetMapping("add")
    public void add(HttpServletResponse response, int z, int x, int y) {
        //https://api.mapbox.com/v4/mapbox.mapbox-streets-v8,ifzm.cjyz8y6hi0pfr2no71j4yzhny-80vkn,ifzm.cjyzh5amz0r4l2up6ibnrpd0j-1r2sr,ifzm.cjyz95qf203yf2qo9yn4fy3u7-99efd/7/104/52.vector.pbf?sku=101qXReOL9KOK&access_token=pk.eyJ1IjoiaWZ6bSIsImEiOiJjamswc3M1a2gwYWEwM3Zxa3ZsMjh3djkwIn0.h93-XtdRdtoC1KbVv_ZIow
        String url = "https://api.mapbox.com/v4/mapbox.mapbox-streets-v8,ifzm.cjyz8y6hi0pfr2no71j4yzhny-80vkn,ifzm.cjyzh5amz0r4l2up6ibnrpd0j-1r2sr,ifzm.cjyz95qf203yf2qo9yn4fy3u7-99efd/%d/%d/%d.vector.pbf?sku=101EZzS1vV2BD&access_token=pk.eyJ1IjoiaWZ6bSIsImEiOiJjamswc3M1a2gwYWEwM3Zxa3ZsMjh3djkwIn0.h93-XtdRdtoC1KbVv_ZIow";
        url = String.format(url, z, x, y);
        String savePath = "F:/wapian/two/" + z + "/";
        String fileName = x + "-" + y + ".vector.pbf";
        File file = new File(savePath + fileName);
        boolean flag = false;
        if (!file.exists()) {
            try {
                flag = downLoadFromUrl(url, fileName, savePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            flag = true;
        }
        if (flag) {
            try {
                FileUtil.out(response, file, fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void downWapian(int z, int x, int y) {
        String url = "https://api.mapbox.com/v4/mapbox.mapbox-streets-v8,ifzm.cjyz8y6hi0pfr2no71j4yzhny-80vkn,ifzm.cjyzh5amz0r4l2up6ibnrpd0j-1r2sr,ifzm.cjyz95qf203yf2qo9yn4fy3u7-99efd/%d/%d/%d.vector.pbf?sku=101EZzS1vV2BD&access_token=pk.eyJ1IjoiaWZ6bSIsImEiOiJjamswc3M1a2gwYWEwM3Zxa3ZsMjh3djkwIn0.h93-XtdRdtoC1KbVv_ZIow";
        url = String.format(url, z, x, y);
        String savePath = "F:/wapian/two/" + z + "/";
        String fileName = x + "-" + y + ".vector.pbf";
        File file = new File(savePath + fileName);

        if (!file.exists()) {
            try {
                downLoadFromUrl(url, fileName, savePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 从网络Url中下载文件
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static boolean downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException{
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(5*1000);
        conn.setReadTimeout(5*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);

        System.out.println("结束");
        //文件保存位置
        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdirs();
        }
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if(fos!=null){
            fos.close();
        }
        if(inputStream!=null){
            inputStream.close();
        }

        System.out.println("info:"+url+" download success");
        return true;
    }

    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

}
