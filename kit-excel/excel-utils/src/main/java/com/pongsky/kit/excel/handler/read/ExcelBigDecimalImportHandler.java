package com.pongsky.kit.excel.handler.read;

import com.pongsky.kit.excel.annotation.ExcelProperty;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * BigDecimal 处理器
 *
 * @author pengsenhao
 **/
public class ExcelBigDecimalImportHandler implements ExcelImportHandler {

    @Override
    public void exec(Object result, Field field, ExcelProperty excelProperty, Object obj) throws IllegalAccessException {
        String str = obj != null
                ? obj.toString()
                : excelProperty.contentStyle().defaultValue();
        int lastIndexOf = str.lastIndexOf(excelProperty.contentStyle().suffix());
        str = StringUtils.isNotBlank(excelProperty.contentStyle().suffix()) && lastIndexOf != -1
                ? str.substring(0, lastIndexOf)
                : str;
        if (StringUtils.isBlank(str)) {
            return;
        }
        BigDecimal value = new BigDecimal(str);
        this.setValue(result, field, value);
    }

}