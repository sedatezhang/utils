package com.pizi.tools.utils.excelUtil;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    /**
     * 读取Excel文件并转换为对象列表
     * @param file 上传的Excel文件
     * @param clazz 对象类类型
     * @return 返回对象列表
     */
    public static <T> List<T> readExcel(MultipartFile file, Class<T> clazz) {
        // 创建一个用于存储结果的列表
        List<T> result = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            // 获取第一个工作表
            Sheet sheet = workbook.getSheetAt(0);

            // 获取表头行
            Row headerRow = sheet.getRow(0);

            // 遍历数据行，从1开始跳过表头
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                // 创建一个新的对象实例
                T obj = clazz.getDeclaredConstructor().newInstance();

                // 遍历每一列，根据字段名匹配值
                for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);
                    String header = headerRow.getCell(j).getStringCellValue();

                    // 根据header获取对应的字段
                    Field field = clazz.getDeclaredField(header);
                    field.setAccessible(true);

                    // 设置字段值，这里只处理了String、Integer、Double、Boolean类型
                    if (cell != null) {
                        if (field.getType() == String.class) {
                            field.set(obj, cell.getStringCellValue());
                        } else if (field.getType() == Integer.class || field.getType() == int.class) {
                            field.set(obj, (int) cell.getNumericCellValue());
                        } else if (field.getType() == Double.class || field.getType() == double.class) {
                            field.set(obj, cell.getNumericCellValue());
                        } else if (field.getType() == Boolean.class || field.getType() == boolean.class) {
                            field.set(obj, cell.getBooleanCellValue());
                        }
                    }
                }

                // 将构建的对象加入结果列表
                result.add(obj);
            }
        } catch (Exception e) {
            // TODO: 异常处理可以根据业务需求自定义
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 将对象列表写入Excel并实现浏览器下载
     * @param dataList 要写入的数据列表
     * @param response HttpServletResponse用于输出文件流
     */
    public static <T> void writeExcel(List<T> dataList, HttpServletResponse response) {
        try {
            // 创建一个新的Excel工作簿
            Workbook workbook = new XSSFWorkbook();
            // 创建一个工作表
            Sheet sheet = workbook.createSheet("Sheet1");

            // 如果数据为空则不处理
            if (dataList.isEmpty()) {
                return;
            }

            // 获取对象的Class对象以反射获取字段
            Class<T> clazz = (Class<T>) dataList.get(0).getClass();

            // 创建表头行
            Row headerRow = sheet.createRow(0);
            Field[] fields = clazz.getDeclaredFields();

            // 填充表头
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(field.getName());
            }

            // 填充数据行
            for (int i = 0; i < dataList.size(); i++) {
                Row dataRow = sheet.createRow(i + 1);
                T data = dataList.get(i);

                for (int j = 0; j < fields.length; j++) {
                    Field field = fields[j];
                    field.setAccessible(true);

                    Cell cell = dataRow.createCell(j);
                    Object value = field.get(data);

                    // 支持常见的几种数据类型
                    if (value instanceof String) {
                        cell.setCellValue((String) value);
                    } else if (value instanceof Integer || value instanceof Double || value instanceof Boolean) {
                        cell.setCellValue(value.toString());
                    } else {
                        // TODO: 可以根据需要扩展支持更多类型
                        cell.setCellValue("");
                    }
                }
            }

            // 设置响应头信息，以便浏览器识别这是一个Excel文件并触发下载
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=export.xlsx");

            // 输出到响应流
            try (OutputStream out = response.getOutputStream()) {
                workbook.write(out);
            }
        } catch (Exception e) {
            // TODO: 异常处理可以根据业务需求自定义
            e.printStackTrace();
        }
    }
}