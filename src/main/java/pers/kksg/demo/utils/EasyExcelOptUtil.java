package pers.kksg.demo.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @project: demo
 * @description: EasyExcelOptUtil
 * @author: lvqiang
 * @create: 2025-01-14 10:54
 **/
public class EasyExcelOptUtil {

    public static void main(String[] args) {


        String inputFilePath = "/Users/lvqiang/Desktop/新颜全景雷达问题回溯/splite_%s"; // 修改为CSV文件路径
//        String inputFilePath = "/Users/lvqiang/Desktop/新颜全景雷达问题回溯/split_1.csv"; // 修改为CSV文件路径
        String outputDirPath = "/Users/lvqiang/Desktop/新颜全景雷达问题回溯/moth_data/";

        for (int i = 1; i <= 30; i++) {
            List<List<String>> lists = new ArrayList<>();
            FileUtil.readLines(String.format(inputFilePath, i), CharsetUtil.CHARSET_UTF_8).forEach(line -> {
                List<String> strings = new ArrayList<>();
                String[] split = line.split(",");
                split[0] = TripleDESEncryptionUtils.decrypt(split[0].replace("\"",""), "bxFAMoR8xWsTkr2JWEz3TtjUptZjXS");
                split[1] = TripleDESEncryptionUtils.decrypt(split[1].replace("\"",""), "bxFAMoR8xWsTkr2JWEz3TtjUptZjXS");
                split[2] = TripleDESEncryptionUtils.decrypt(split[2].replace("\"",""), "bxFAMoR8xWsTkr2JWEz3TtjUptZjXS");
                strings.add(split[0]);
                strings.add(split[1]);
                strings.add(split[2]);
                lists.add(strings);
            });
            CsvWriter writer = CsvUtil.getWriter(new File(String.format(outputDirPath + "excel_%s.csv", i)), Charset.forName("UTF-8"));
            writer.write(lists);
            writer.close();
        }
    }
}
