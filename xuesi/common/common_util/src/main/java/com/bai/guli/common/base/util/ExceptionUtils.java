package com.bai.guli.common.base.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils {
    public static String getMessage(Exception e){
        StringWriter sw =null;
        PrintWriter pw=null;
        try {
            sw =new StringWriter();
            pw= new PrintWriter(sw);
            // 将出错的栈信息输出到printWriter中
            e.printStackTrace(pw);
            sw.flush();
            pw.flush();
        } finally {
            if (sw !=null){
                try {
                    sw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (pw !=null){
                try {
                    pw.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return sw.toString();
    }
}
