package com.kuugen.core.util;

import com.kuugen.modular.stock.model.TbStockDaykModel;
import sun.security.util.AuthResources_it;

import java.math.BigDecimal;
import java.util.List;
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
    public static boolean isEmpty(String str){
        if(str == null || "".equals(str)){
            return true;
        }else
        {
            return false;
        }
    }
    /**
     * 获取平均数,作为均线数据
     * @param days
     * @param string
     * @return
     */
    public static double getAvgPrice(List<TbStockDaykModel> days, String now_price) {
        // TODO Auto-generated method stub
        double sumPrice=0;
        double avgPrice=0;
        for(TbStockDaykModel dayinfo:days){
            sumPrice+=Double.parseDouble(dayinfo.getClose_price());
        }
        sumPrice+=Double.parseDouble(now_price);
        avgPrice = sumPrice/(days.size()+1);
        avgPrice =new BigDecimal(avgPrice).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
        return avgPrice;
    }
    /**
     * 两个double字符串对比大小
     * @param now_price
     * @param other_price
     * @return
     */
    public static boolean ifOvenPrice(String now_price,String other_price){
        boolean flag=true;
        if(Double.parseDouble((now_price))>=Double.parseDouble(formatDouble(other_price))){

        }else{
            flag =false;
        }
        return flag;
    }
    public static String formatDouble(String db){
        if (strnull(db).equals(""))
            return db;
        else
            return String.valueOf((double)Math.round(Float.valueOf(db)*100)/100);
    }
    /**
     * 为检查null值，如果为null，将返回""，不为空时将替换其非html符号
     * @param strn
     * @return
     */
    public static String strnull(String strn) {
        return strnull(strn, "");
    }
    /**
     * 如果入参是null或者"",用另一入参rpt替代入参返回，否则返回入参的trim()
     * @param Str
     * @return
     */
    public static String strnull(String Str, String rpt) {
        if (Str == null || Str.equals("null") || Str.equals("") || Str.trim() == null) {
            return rpt;
        }
        else {
            Str=Str.replaceAll("undefined", "");
            return Str.trim();
        }
    }
    /**
     * 为检查null值，如果为null，将返回""，不为空时将替换其非html符号
     * @param strn
     * @return
     */
    public static String strnull(Object str) {
        String result = "";
        if (str == null) {
            result = "";
        }
        else {
            result =str.toString();
        }
        return result;
    }
    /**
     * 验证批量股票数据正则表达式
     */
    public static boolean checkStockStr(String stocks) {
        boolean flag = false;
        try {
            String check = "^([0-9]{6}[,])*[0-9]{6}[,]?$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(stocks);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
    public static void main(String[] args){
        System.out.println( extractDoubleForStr("大大21.22啊大大撒"));
    }
}
