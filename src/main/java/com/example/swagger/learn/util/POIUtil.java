package com.example.swagger.learn.util;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class POIUtil {

    private Workbook getReadWorkBookType(String filePath) throws Exception {
        //xls-2003, xlsx-2007
        FileInputStream is = null;
        try {

            is = new FileInputStream(new File(filePath));
            return new XSSFWorkbook(is);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }


    public List<Map<String, Object>> readExcel(String sourceFilePath) throws Exception {
        Workbook workbook = null;

        try {
            workbook = getReadWorkBookType(sourceFilePath);
            List<Map<String, Object>> contents = new ArrayList();

            //获取第一个sheet
            Sheet sheet = workbook.getSheetAt(3);
            //第0行是表名，忽略，从第二行开始读取
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                // todo 可以将数据封装成map   vertex  label  vertex
                if (row.getCell(0) != null && row.getCell(1) != null && row.getCell(1) != null) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("from", row.getCell(0).getStringCellValue());
                    map.put("label", row.getCell(1).getStringCellValue());
                    map.put("to", row.getCell(2).getStringCellValue());
                    contents.add(map);
                }
            }
            return contents;
        } finally {
            IOUtils.closeQuietly(workbook);
        }
    }

    public List<Map<String, Object>> readExcel2(String sourceFilePath) throws Exception {
        Workbook workbook = null;

        try {
            workbook = getReadWorkBookType(sourceFilePath);
            List<Map<String, Object>> contents = new ArrayList();

            //获取第一个sheet
            Sheet sheet = workbook.getSheetAt(2);
            //第0行是表名，忽略，从第二行开始读取
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                // todo 可以将数据封装成map   vertex  label  vertex
                try{
                    if (row.getCell(0) != null  && row.getCell(2) != null) {
                        Map<String, Object> map = new HashMap<>();
                        map.put(row.getCell(2).getStringCellValue(), row.getCell(0).getStringCellValue());
                        contents.add(map);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return contents;
        } finally {
            IOUtils.closeQuietly(workbook);
        }
    }


    public List<String> readExcel1(String sourceFilePath) throws Exception {
        Workbook workbook = null;

        try {
            workbook = getReadWorkBookType(sourceFilePath);

            List<String> stringList = new ArrayList<>();
            //获取第一个sheet
            Sheet sheet = workbook.getSheetAt(3);
            //第0行是表名，忽略，从第二行开始读取
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                // todo 可以将数据封装成map   vertex  label  vertex


                Cell cell = row.getCell(0);
                try {
                    stringList.add( cell.getStringCellValue());
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            System.out.println(stringList.size());
            return stringList;
        } finally {
            IOUtils.closeQuietly(workbook);
        }
    }
}
