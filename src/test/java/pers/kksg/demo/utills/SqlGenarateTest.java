package pers.kksg.demo.utills;

import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * SqlGenarate
 *
 * @Author Jonlv
 * @Description TODO
 * @Date 2022/6/9 19:58
 */
public class SqlGenarateTest {
    String daijikaSQL = "('%s', '', '', 1, %s, %s, 0, 1, '', now(), now()),";
    String jiejikaSQL = "('%s', '', '', 2, %s, %s, 0, 1, '', now(), now()),";

    String jiejiUpdateSQL = "UPDATE lrb.t_base_bank_pay_limit t SET t.F_order_limit = %s, t.F_daily_limit = %s WHERE t.F_bank_name = '%s' and F_card_type = 2;";
    String daijiUpdateSQL = "UPDATE lrb.t_base_bank_pay_limit t SET t.F_order_limit = %s, t.F_daily_limit = %s WHERE t.F_bank_name = '%s' and F_card_type = 1;";

    @Test
    public void test(){
        String insertExcelPath = "C:\\Users\\Yeahka\\Desktop\\insert.xls";
        String updateExcelPath = "C:\\Users\\Yeahka\\Desktop\\update.xls";
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file(insertExcelPath));
        List<String> list = new ArrayList<>();
        reader.read().forEach(row ->{
            if (StringUtils.isBlank((String) row.get(0))) {
                return;
            }
            String bankName = (String) row.get(0);
            jiejiBuild(list, row, bankName);

            daijiBuild(list, row, bankName);
        });

        list.forEach(str ->{
            System.out.println(str);
        });
    }

    private void daijiBuild(List<String> list, List<Object> row, String bankName) {
        Object o = row.get(3);
        if ( o instanceof  String ) {
            return;
        }
        Long daijisignle = (Long) row.get(3);
        Long daijidaily = (Long) row.get(4);
        list.add(String.format(daijikaSQL, bankName, daijisignle * 100, daijidaily * 100));
    }

    private void jiejiBuild(List<String> list, List<Object> row, String bankName) {
        Object o = row.get(2);
        if ( o instanceof  String ) {
            return;
        }
        Long jiejisignle = (Long) row.get(1);
        Long jiejidaily = (Long) row.get(2);
        list.add(String.format(jiejikaSQL, bankName, jiejisignle * 100, jiejidaily * 100));
    }


    @Test
    public void test2(){
        String updateExcelPath = "C:\\Users\\Yeahka\\Desktop\\update.xls";
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file(updateExcelPath));
        List<String> list = new ArrayList<>();
        reader.read().forEach(row ->{
            if (StringUtils.isBlank((String) row.get(0))) {
                return;
            }
            String bankName = (String) row.get(0);

            updateJieji(list, row, bankName);

            updateDaiji(list, row, bankName);
        });

        list.forEach(str ->{
            System.out.println(str);
        });
    }

    private void updateDaiji(List<String> list, List<Object> row, String bankName) {
        Object o = row.get(3);
        if ( o instanceof  String ) {
            return;
        }
        Long daijisignle = (Long) row.get(3);
        Long daijidaily = (Long) row.get(4);
        list.add(String.format(daijiUpdateSQL, daijisignle * 100, daijidaily * 100, bankName));
    }

    private void updateJieji(List<String> list, List<Object> row, String bankName) {
        Object o = row.get(2);
        if ( o instanceof  String ) {
            return;
        }
        Long jiejisignle = (Long) row.get(1);
        Long jiejidaily = (Long) row.get(2);
        list.add(String.format(jiejiUpdateSQL, jiejisignle * 100, jiejidaily * 100, bankName));
    }

}
