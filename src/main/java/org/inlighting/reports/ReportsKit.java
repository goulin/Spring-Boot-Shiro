package org.inlighting.reports;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;


import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;


/**
 * 报表工具
 * @author goulin
 * @Title: ${file_name}
 * @Description: ${todo}
 * @date 2018/4/16上午9:15
 */
public class ReportsKit {

    public static void main(String[] args) {

        JasperReportBuilder report = DynamicReports.report();// 创建空报表
        // 样式
        //加粗
        StyleBuilder boldStl = DynamicReports.stl.style().bold();
        //加粗样式
        StyleBuilder boldCenteredStl = DynamicReports.stl.style(boldStl)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
        //整页头标题样式
        StyleBuilder titleStl = DynamicReports.stl.style(boldCenteredStl)
                .setFontSize(16);
        //列标题样式
        StyleBuilder columnTitleStl = DynamicReports.stl.style(boldCenteredStl)
                .setBorder(DynamicReports.stl.pen1Point())
                .setBackgroundColor(Color.LIGHT_GRAY);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<>();
        map.put("id", 123);
        map.put("code", "185");
        map.put("service", "中国移动");
        map.put("province", "重庆");
        map.put("city", "重庆");
        map.put("type", "apple");
        map.put("name", "测试");
        list.add(map);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("id", 124);
        map1.put("code", "186");
        map1.put("service", "中国移动");
        map1.put("province", "重庆");
        map1.put("city", "重庆");
        map1.put("type", "HW");
        map1.put("name", "测试");
        list.add(map1);

        report.columns(
                Columns.column("ID", "id", DataTypes.integerType())
                        .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER),
                Columns.column("手机号段", "code", DataTypes.stringType())
                        .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER),
                Columns.column("运营商", "service", DataTypes.stringType())
                        .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER),
                Columns.column("省份", "province", DataTypes.stringType())
                        .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER),
                Columns.column("城市", "city", DataTypes.stringType())
                        .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER),
                Columns.column("品牌", "type", DataTypes.stringType())
                        .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER))
                .setColumnTitleStyle(columnTitleStl)
                .setHighlightDetailEvenRows(true)
                .title(Components.text("手机号段").setStyle(titleStl))
                // 标题
                .pageFooter(Components.pageXofY().setStyle(boldCenteredStl))
                .setDataSource(list);// 数据源

        try {
            // 显示报表
            report.show();
            //report.toXls(new FileOutputStream("F:/test.xls"));
            // 生成PDF文件
            // report.toPdf(new FileOutputStream("F:/test.pdf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
