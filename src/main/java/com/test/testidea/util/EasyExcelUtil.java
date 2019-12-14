package com.test.testidea.util;

import com.alibaba.excel.EasyExcel;
import com.test.testidea.domain.emi.EasyExcelUser;
import java.util.List;

/**
 * 测试easyExcel
 *
 * @author zhangshuai
 * @date 2019/11/5 15:44
 */
public class EasyExcelUtil {

    public static void main(String[] args) {
        String fileName = "D://demo.xlsx";
        List<EasyExcelUser> list = EasyExcel.read(fileName).head(EasyExcelUser.class).sheet().doReadSync();
        for (EasyExcelUser data : list) {
            String sql = "INSERT INTO `env_v2`.`dic_national_air_station`( `address`, `area`, `city`, `city_code`, `city_type_code`, `lat`, `lon`, `province`, `station_code`) VALUES ( '"+ data.getAddress() +"', '"+data.getArea()+"', '"+data.getCity()+"', '"+data.getCityCode()+"', '"+data.getCityTypeCode()+"', '"+data.getLat()+"', '"+data.getLon()+"', '"+data.getProvince()+"', '"+data.getStationCode()+"');";
            System.out.println(sql);
        }

        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 写法1：
//        fileName = "D://demo.xls";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
//        EasyExcel.read(fileName, EasyExcelUser.class, new EasyExcelUserListener()).sheet().doRead();
//
//        // 写法2：
//        fileName = "D://demo.xls";
//        ExcelReader excelReader = EasyExcel.read(fileName, EnvirStationExcel.class, new DemoDataListener()).build();
//        ReadSheet readSheet = EasyExcel.readSheet(0).build();
//        excelReader.read(readSheet);
//        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
//        excelReader.finish();

    }
}
