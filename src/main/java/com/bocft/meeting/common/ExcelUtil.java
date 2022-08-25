package com.bocft.meeting.common;

import com.alibaba.excel.EasyExcel;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

//导出excel相关
public class ExcelUtil {
    /***
     *
     * @param response http响应
     * @param tClass   要发送的class 如Student.class，记得在成员变量上加excel的注解，每个成员变量对应列
     * @param name    文件名称
     * @param data    多条记录的话，数据的list集合
     */
    public static void exportData(HttpServletResponse response, Class<?> tClass, String name, List<?> data) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode(name, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), tClass).sheet(name).doWrite(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
