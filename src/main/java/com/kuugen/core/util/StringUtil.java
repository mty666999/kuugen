package com.kuugen.core.util;

import sun.security.util.AuthResources_it;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: mty
 * @Description:自己动手丰衣足食
 * @Date: Created in 11:15 2019-05-09
 * @Modified By:
 */
public class StringUtil {
    /**
     * 通过正则表达式抽取数据
     * @return
     */
    public static double extractDoubleForStr(String text){
        double num=0.0;
        String regEx="[^0-9.]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(text);
        num = Double.parseDouble( m.replaceAll("").trim());
        return num;
    }
    public static int extractIntForStr(String text){
        int num=0;
        String regEx="[^0-9.]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(text);
        num = Integer.parseInt( m.replaceAll("").trim());
        return num;
    }
    public static void main(String[] args){
        System.out.println( extractDoubleForStr("大大21.22啊大大撒"));
    }
}
