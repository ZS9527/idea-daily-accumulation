package com.test.testidea.web.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.geojson.feature.FeatureJSON;

/**
 * TODO
 *
 * @author zhangshuai
 * @date 2019/7/22 14:20
 */
public class SpliceGeoJson {

    public static void main(String[] args) {
//        File fileJson = new File("D:\\Downloads\\GeoJSON\\xianjie.json");
//        if (fileJson.isFile()) {
//            FileReader filereader= null;
//            String json = "";
//            try {
//                filereader = new FileReader("D:\\Downloads\\GeoJSON\\xianjie.json");
//                BufferedReader bufferedReader=new BufferedReader(filereader);
//                String read;
//                while((read=bufferedReader.readLine())!=null){
//                    json = json + read;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            JSONObject jsonFile = JSON.parseObject(json);
//            try {
//                OutputStream os = new FileOutputStream("D:\\Downloads\\GeoJSON\\output.geojson");
//                PrintWriter pw=new PrintWriter(os);
//                pw.print(jsonFile.toJSONString());
//                pw.close();
//                os.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

//        File file = new File("D:\\Downloads\\GeoJSON\\sss");
//        if (file.isDirectory()) {
//           File[] files = file.listFiles();
//           String totalJson = "{"
//                + "\"type\": \"FeatureCollection\","
//                + "\"features\": [";
//           for (int i = 0; i < files.length; i++) {
//               File xianGeoJsonFile = files[i];
//               String json = "";
//               try {
//                   if (xianGeoJsonFile.getName().contains("shi.json")) {
//                        continue;
//                   }
//                   FileReader filereader=new FileReader("D:\\Downloads\\GeoJSON\\sss\\" + xianGeoJsonFile.getName());
//                   BufferedReader bufferedReader=new BufferedReader(filereader);
//                   String read;
//                   while((read=bufferedReader.readLine())!=null){
//                       json = json + read;
//                   }
//               } catch (IOException e) {
//                   e.printStackTrace();
//               }
//               if (i != files.length -1) {
//                   totalJson = totalJson + json + ",";
//               }else{
//                   totalJson = totalJson + json + "]}";
//               }
//
//           }
//            try {
//                OutputStream os = new FileOutputStream("D:\\Downloads\\GeoJSON\\sss\\output.geojson");
//                PrintWriter pw=new PrintWriter(os);
//                pw.print(totalJson);
//                pw.close();
//                os.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }

        File fileJson = new File("D:\\zjtq\\pre.txt");
        if (fileJson.isFile()) {
            FileReader filereader= null;
            String json = "";
            try {
                filereader = new FileReader("D:\\zjtq\\pre.txt");
                BufferedReader bufferedReader=new BufferedReader(filereader);
                String read;
                while((read=bufferedReader.readLine())!=null){
                    json = json + read;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            JSONObject jsonFile = JSON.parseObject(json);
            JSONArray objects = jsonFile.getJSONArray("features");
            for (int i = 0; i < objects.size(); i++) {

                JSONObject object = objects.getJSONObject(i);
                JSONObject geometry = object.getJSONObject("geometry");
                JSONArray[][] coordinates = geometry.getObject("coordinates", JSONArray[][].class);
                for (int x = 0; x < coordinates.length; x++) {
                    JSONArray[] oneArray = coordinates[x];
                    for (int y = 0; y < oneArray.length; y++) {
                        JSONArray one = oneArray[y];
                        for (int z = 0; z < one.size(); z++) {
                            BigDecimal bigDecimal = one.getBigDecimal(z);
                            one.set(z, bigDecimal.setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue());
                        }
                    }
                }
                JSONArray first = JSON.parseArray(String.valueOf(coordinates[0][0]));
                JSONArray last =  JSON.parseArray(String.valueOf(coordinates[0][coordinates[0].length-1]));
                if (!first.equals(last)) {
                    coordinates[0][coordinates[0].length-1] = coordinates[0][0];
                    geometry.put("coordinates", coordinates);
                }
                JSONObject oneFile = new JSONObject();
                oneFile.put("features", new JSONArray().add(object));

            }
            // 字符串流
            try {
                ByteArrayInputStream bis = null;
                SimpleFeatureCollection fs = null;
                bis = new ByteArrayInputStream(jsonFile.toJSONString().getBytes());
                // geojson读取工具
                FeatureJSON fjson = new FeatureJSON();
                // 获取要素集合
                fs = (SimpleFeatureCollection) fjson.readFeatureCollection(bis);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                OutputStream os = new FileOutputStream("D:\\zjtq\\output.geojson");
                PrintWriter pw=new PrintWriter(os);
                pw.print(jsonFile.toJSONString());
                pw.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
