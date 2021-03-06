package com.pongsky.kit.excel.handler.export;

import com.pongsky.kit.excel.annotation.ExcelProperty;
import com.pongsky.kit.excel.entity.ExcelExportInfo;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Boolean 处理器
 *
 * @author pengsenhao
 **/
public class ExcelBooleanExportHandler implements ExcelExportHandler {

    private static final Map<Boolean, String> MAP = new HashMap<Boolean, String>(4) {{
        put(Boolean.TRUE, "是");
        put(Boolean.FALSE, "否");
    }};

    @Override
    public void exec(Field field, ExcelProperty excelProperty, Object obj, ExcelExportInfo info) {
        String value = obj == null
                ? excelProperty.contentStyle().defaultValue()
                : MAP.get((Boolean) obj);
        info.getCell().setCellValue(new XSSFRichTextString(value));
        info.setTextWidth(info.getCell().getColumnIndex(), value.length());
    }

}
