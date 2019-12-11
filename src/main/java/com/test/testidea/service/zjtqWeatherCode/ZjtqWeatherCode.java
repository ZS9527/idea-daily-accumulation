package com.test.testidea.service.zjtqWeatherCode;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 整理知节天气天气代码
 *
 * @author zhangshuai
 * @date 2019/11/8 23:58
 */
public class ZjtqWeatherCode {

    public static void main(String[] args) throws IOException {
        Map<String, List<Integer>> cmissToImg = new HashMap<>(36);
        cmissToImg.put("1", Arrays.asList(0,  100));
        cmissToImg.put("2", Arrays.asList(1, 2, 101,3,211,103));
        cmissToImg.put("3", Arrays.asList(294));
        cmissToImg.put("4", Arrays.asList(25, 292, 293, 180, 181, 182, 183, 184, 80, 81, 82));
        cmissToImg.put("5", Arrays.asList(118, 18, 126, 192, 193, 195, 196));
        cmissToImg.put("6", Arrays.asList(27, 288, 289, 290, 291, 189, 87, 88, 89, 90));
        cmissToImg.put("7", Arrays.asList(23, 259, 284, 285, 286, 287, 167, 168, 174, 175, 176, 178, 79, 68, 83, 84));
        cmissToImg.put("8", Arrays.asList(14, 15, 16, 121, 123, 21, 250, 251, 252, 253, 254, 140, 141, 143, 160, 161, 60, 61, 91, 122, 20, 150, 151, 152, 153, 154, 155, 156, 157, 158, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59));
        cmissToImg.put("9", Arrays.asList(264, 162, 62, 63, 92));
        cmissToImg.put("10", Arrays.asList(142, 144, 265, 163, 64, 65));
        cmissToImg.put("11", Arrays.asList(257, 266, 267));
        cmissToImg.put("14", Arrays.asList(26, 185, 186, 187));
        cmissToImg.put("15", Arrays.asList(239, 124, 127, 128, 22, 38, 145, 270, 278, 283, 170, 171, 177, 70, 71, 77, 78, 85, 93));
        cmissToImg.put("16", Arrays.asList(129, 36, 271, 172, 72, 73, 86, 94));
        cmissToImg.put("17", Arrays.asList(37, 39, 146, 272, 173, 74, 75));
        cmissToImg.put("18", Arrays.asList(273, 274, 275, 276, 277));
        cmissToImg.put("19", Arrays.asList(120, 241, 242, 243, 244, 245, 246, 247, 248, 249, 28, 130, 133, 134, 135, 43, 45, 47, 49));
        cmissToImg.put("20", Arrays.asList(125, 24, 147, 148, 279, 281, 282, 164, 165, 166, 66, 67));
        cmissToImg.put("21", Arrays.asList(230, 9, 30, 35, 209));
        cmissToImg.put("27", Arrays.asList(4, 5, 6, 206, 210, 220, 221, 222, 223, 224, 104, 225, 105, 226, 227, 228));
        cmissToImg.put("33", Arrays.asList(112, 13, 17, 29, 190, 191, 194, 95, 96, 97, 98, 99, 213, 217));
        cmissToImg.put("34", Arrays.asList(110, 10, 11, 12, 131, 132, 40, 41, 42, 44, 46, 48));
        cmissToImg.put("35", Arrays.asList(19, 199, 219));

        Map<String, String> nameToImg = new HashMap<>(36);
        nameToImg.put("1", "晴");
        nameToImg.put("2", "多云");
        nameToImg.put("3", "阴");
        nameToImg.put("4", "阵雨");
        nameToImg.put("5", "雷阵雨");
        nameToImg.put("6", "冰雹");
        nameToImg.put("7", "雨夹雪");
        nameToImg.put("8", "小雨");
        nameToImg.put("9", "中雨");
        nameToImg.put("10", "大雨");
        nameToImg.put("11", "暴雨");
        nameToImg.put("12", "大暴雨");
        nameToImg.put("13", "特大暴雨");
        nameToImg.put("14", "阵雪");
        nameToImg.put("15", "小雪");
        nameToImg.put("16", "中雪");
        nameToImg.put("17", "大雪");
        nameToImg.put("18", "暴雪");
        nameToImg.put("19", "雾");
        nameToImg.put("20", "冻雨");
        nameToImg.put("21", "沙尘暴");
        nameToImg.put("22", "浮尘");
        nameToImg.put("23", "扬沙");
        nameToImg.put("24", "强沙尘暴");
        nameToImg.put("25", "浓雾");
        nameToImg.put("26", "强浓雾");
        nameToImg.put("27", "霾");
        nameToImg.put("28", "中度霾");
        nameToImg.put("29", "重度霾");
        nameToImg.put("30", "严重霾");
        nameToImg.put("31", "大雾");
        nameToImg.put("32", "特强浓雾");
        nameToImg.put("33", "雷电");
        nameToImg.put("34", "轻雾");
        nameToImg.put("35", "龙卷风");

        Map<String, List<Integer>> ocfToImg = new HashMap<>(36);
        ocfToImg.put("1",  Arrays.asList(00));
        ocfToImg.put("2",  Arrays.asList(01));
        ocfToImg.put("3",  Arrays.asList(02));
        ocfToImg.put("4",  Arrays.asList(03));
        ocfToImg.put("5",  Arrays.asList(04));
        ocfToImg.put("6",  Arrays.asList(05));
        ocfToImg.put("7",  Arrays.asList(06));
        ocfToImg.put("8",  Arrays.asList(07, 301));
        ocfToImg.put("9",  Arrays.asList(8, 21));
        ocfToImg.put("10",  Arrays.asList(9, 22));
        ocfToImg.put("11",  Arrays.asList(10, 23));
        ocfToImg.put("12",  Arrays.asList(11, 24));
        ocfToImg.put("13",  Arrays.asList(12, 25));
        ocfToImg.put("14",  Arrays.asList(13));
        ocfToImg.put("15",  Arrays.asList(14, 302));
        ocfToImg.put("16",  Arrays.asList(15, 26));
        ocfToImg.put("17",  Arrays.asList(16, 27));
        ocfToImg.put("18",  Arrays.asList(17, 28));
        ocfToImg.put("19",  Arrays.asList(18));
        ocfToImg.put("20",  Arrays.asList(19));
        ocfToImg.put("21",  Arrays.asList(20));
        ocfToImg.put("22",  Arrays.asList(29));
        ocfToImg.put("23",  Arrays.asList(30));
        ocfToImg.put("24",  Arrays.asList(31));
        ocfToImg.put("25",  Arrays.asList(32));
        ocfToImg.put("26",  Arrays.asList(49));
        ocfToImg.put("27",  Arrays.asList(53));
        ocfToImg.put("28",  Arrays.asList(54));
        ocfToImg.put("29",  Arrays.asList(55));
        ocfToImg.put("30",  Arrays.asList(56));
        ocfToImg.put("31",  Arrays.asList(57));
        ocfToImg.put("32",  Arrays.asList(58));

    }
}
