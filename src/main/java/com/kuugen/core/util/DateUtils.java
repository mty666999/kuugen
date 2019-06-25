package com.kuugen.core.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtils {
	public DateUtils()
    {
    }
	
	/**将Date转换成字符串.
     * @param date 时间
     * @param pattern 日期字符串匹配模式 例:yyyy-MM-dd HH:mm:ss
     */
    public static final String getDateString(Date date, String dateSimpleFormat){
        SimpleDateFormat formattxt = new SimpleDateFormat(dateSimpleFormat);
        return formattxt.format(date);
    }
    /**将Date转换成字符串.
     * @param date 时间
     * @param pattern 日期字符串匹配模式 例:yyyy-MM-dd
     */
    public static String DateToDateString(Date date){
    	if (date==null) {
			return null;
		}
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	return format.format(date);
    }

    public static final Date getDate(Date date, String dateSimpleFormat){
        return getDate(getDateString(date, dateSimpleFormat), dateSimpleFormat);
    }

    public static final Date getDate(String dateTimeString, String dateSimpleFormat){
        SimpleDateFormat formattxt = new SimpleDateFormat(dateSimpleFormat);
        Date date = null;
        try
        {
            date = formattxt.parse(dateTimeString);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            date = new Date();
        }
        return date;
    }

    public static final Date getYesterdayString(Date date){
        Calendar yesterday = Calendar.getInstance();
        yesterday.setTime(date);
        yesterday.add(11, -24);
        return yesterday.getTime();
    }

    public static final Date getLastMonthString(Date date){
        Calendar lastMonth = Calendar.getInstance();
        lastMonth.setTime(date);
        lastMonth.add(2, -1);
        return lastMonth.getTime();
    }

    public static final Date getNextMonthString(Date date){
        Calendar nextMonth = Calendar.getInstance();
        nextMonth.setTime(date);
        nextMonth.add(2, 1);
        return nextMonth.getTime();
    }

    public static Date getMonthFirstDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, calendar.getActualMinimum(5));
        return calendar.getTime();
    }

    public static String getMonthFirstDay(String dateTimeString, String dateSimpleFormat){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDate(dateTimeString, dateSimpleFormat));
        calendar.set(5, calendar.getActualMinimum(5));
        return getDateString(calendar.getTime(), dateSimpleFormat);
    }

    public static int getLastYear(String dateTimeString, String dateSimpleFormat){
        return Integer.parseInt(getDateString(getLastMonthString(getDate(dateTimeString, dateSimpleFormat)), "yyyy"));
    }

    public static int getLastMonth(String dateTimeString, String dateSimpleFormat){
        return Integer.parseInt(getDateString(getLastMonthString(getDate(dateTimeString, dateSimpleFormat)), "MM"));
    }

    public static Date getNowDate(){
        Date nowDate = null;
        try{
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateTime = format.format(date);
            nowDate = format.parse(dateTime);
        }
        catch(ParseException e){
            e.printStackTrace();
        }
        return nowDate;
    }

    public static Timestamp dateToTime(Date date, String dateSimpleFormat){
        SimpleDateFormat df = new SimpleDateFormat(dateSimpleFormat);
        String str = df.format(date);
        return Timestamp.valueOf(str);
    }

    public static Date timeToDate(Timestamp time, String dateSimpleFormat){
        Date date = null;
        try
        {
            SimpleDateFormat df = new SimpleDateFormat(dateSimpleFormat);
            String str = df.format(time);
            date = df.parse(str);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return date;
    }

    public static Date isOneHour(String startDateStr, String endDateStr){
        String nowDateStr = getDateString(getNowDate(), "yyyy-MM-dd HH:mm:ss");
        String yearn = nowDateStr.substring(0, 4);
        String yeare = endDateStr.substring(0, 4);
        String monthn = nowDateStr.substring(5, 7);
        String monthe = endDateStr.substring(5, 7);
        String daten = nowDateStr.substring(8, 10);
        String datee = endDateStr.substring(8, 10);
        String hours = startDateStr.substring(11, 13);
        if(!yearn.equals(yeare))
            return null;
        if(!monthn.equals(monthe))
            return null;
        if(!daten.equals(datee))
            return null;
        String timeStr = (new StringBuilder()).append(nowDateStr.substring(0, 10)).append(" ").append(String.valueOf(Integer.parseInt(hours, 10) + 1)).append(":").append(startDateStr.substring(14, 19)).toString();
        Date time = getDate(timeStr, "yyyy-MM-dd HH:mm:ss");
        if(nowDateStr.compareTo(timeStr) > 0)
            return null;
        else
            return time;
    }

    public static Date isOneDate(String startDateStr, String endDateStr){
        String nowDateStr = getDateString(getNowDate(), "yyyy-MM-dd HH:mm:ss");
        String yearn = nowDateStr.substring(0, 4);
        String yeare = endDateStr.substring(0, 4);
        String monthn = nowDateStr.substring(5, 7);
        String monthe = endDateStr.substring(5, 7);
        String dates = startDateStr.substring(8, 10);
        if(!yearn.equals(yeare))
            return null;
        if(!monthn.equals(monthe))
            return null;
        String dateStr = (new StringBuilder()).append(nowDateStr.substring(0, 7)).append("-").append(String.valueOf(Integer.parseInt(dates, 10) + 1)).append(" ").append(startDateStr.substring(11, 19)).toString();
        Date date = getDate(dateStr, "yyyy-MM-dd HH:mm:ss");
        if(nowDateStr.compareTo(dateStr) > 0)
            return null;
        else
            return date;
    }

    public static Date isOneMonth(String startDateStr, String endDateStr){
        String nowDateStr = getDateString(getNowDate(), "yyyy-MM-dd HH:mm:ss");
        String yearn = nowDateStr.substring(0, 4);
        String yeare = endDateStr.substring(0, 4);
        String months = startDateStr.substring(5, 7);
        if(!yearn.equals(yeare) && Integer.parseInt(months) != 12)
            return null;
        if(Integer.parseInt(months) == 12)
            months = "0";
        int m = Integer.parseInt(months, 10) + 1;
        String monthStr = null;
        if(m < 10)
            monthStr = (new StringBuilder()).append(nowDateStr.substring(0, 4)).append("-0").append(String.valueOf(Integer.parseInt(months, 10) + 1)).append("-").append(startDateStr.substring(8, 19)).toString();
        else
            monthStr = (new StringBuilder()).append(nowDateStr.substring(0, 4)).append("-").append(String.valueOf(Integer.parseInt(months, 10) + 1)).append("-").append(startDateStr.substring(8, 19)).toString();
        System.out.println(monthStr);
        Date month = getDate(monthStr, "yyyy-MM-dd HH:mm:ss");
        if(nowDateStr.compareTo(monthStr) > 0)
            return null;
        else
            return month;
    }

    public static Date isOneYear(String startDateStr, String endDateStr){
        String nowDateStr = getDateString(getNowDate(), "yyyy-MM-dd HH:mm:ss");
        String years = startDateStr.substring(0, 4);
        String yearStr = (new StringBuilder()).append(String.valueOf(Integer.parseInt(years.substring(0, 4)) + 1)).append("-").append(startDateStr.substring(5, 19)).toString();
        Date date = getDate(yearStr, "yyyy-MM-dd HH:mm:ss");
        if(nowDateStr.compareTo(yearStr) > 0)
            return null;
        else
            return date;
    }

    public static int betweenMonth(Date d1, Date d2){
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        boolean flag = true;
        int temp = 0;
        while(flag) 
        {
            temp++;
            c1.add(2, 1);
            if(c1.before(c2))
                flag = true;
            else
                flag = false;
        }
        return temp;
    }

    public static int betweenHour(Date d1, Date d2){
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        boolean flag = true;
        int temp = 0;
        while(flag) 
        {
            temp++;
            c1.add(10, 1);
            if(c1.before(c2))
                flag = true;
            else
                flag = false;
        }
        return temp;
    }
    
    /**
	 * 获取最近一周的日期
	 * @return String
	 */
	public static String PreviousWeekToDate(){
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DATE, -7);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date.getTime());
	}
	
	public static String getBeforeMonthToStr(){
		Calendar date = Calendar.getInstance();
		date.add(Calendar.MONTH, -1);
		date.add(Calendar.DATE, -1);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date.getTime());
	}
	
	public static String getBeforeMonthToNumber(){
		Calendar date = Calendar.getInstance();
		date.add(Calendar.MONTH, -1);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		return formatter.format(date.getTime());
	}
	
	public static String getBeforeDateToStr(){
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DATE, -1);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date.getTime());
	}
	
	public static String getMonth(int num){
		Calendar date = Calendar.getInstance();
		date.add(Calendar.MONTH, num);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date.getTime());
	}
	
	public static Date getDate(String dateStr){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return formatter.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getDateStr(){
		Calendar date = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date.getTime());
	}
	
	public static boolean checkDate(String sDate) {
		String eL = "^((//d{2}(([02468][048])|([13579][26]))[//-/////s]?((((0?[13578])|(1[02]))[//-/////s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[//-/////s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[//-/////s]?((0?[1-9])|([1-2][0-9])))))|(//d{2}(([02468][1235679])|([13579][01345789]))[//-/////s]?((((0?[13578])|(1[02]))[//-/////s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[//-/////s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[//-/////s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(//s(((0?[0-9])|([1][0-9])|([2][0-3]))//:([0-5]?[0-9])((//s)|(//:([0-5]?[0-9])))))?$";
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(sDate);
		boolean b = m.matches();
		if (b) {
			return b;
		}else {
			String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";		
			String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"				
			+ "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"				
					+ "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
			+ "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("				
					+ "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"				
			+ "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
			Pattern pattern = Pattern.compile(datePattern1);			
			Matcher match = pattern.matcher(sDate);			
			if (match.matches()) {				
				pattern = Pattern.compile(datePattern2);				
				match = pattern.matcher(sDate);				
				return match.matches();			
			}		
			return false;
		}
	}
	
	/**
	 * 返回当前时间
	 * 
	 * @return 返回当前时间
	 */
	public static Date getCurrentDateTime() {
		Calendar calNow = Calendar.getInstance();
		Date dtNow = calNow.getTime();
		return dtNow;
	}
	
	/**
	 * 返回当前季度1,2,3,4
	 * @return
	 */
	public static int getNowJidu() {
		Calendar cal = Calendar.getInstance();
		double month = cal.get(Calendar.MONTH )+1;
		int jidu =(int) Math.ceil(month/3.0);
		return jidu;
	}
	/**
	 * 获取当前年份
	 * @return
	 */
	public static int getNowYear() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return year;
	}
	public static boolean isWorkDay(Date date){
		boolean flag=true;
		String week=dateToWeek(DateToDateString(date));
		if("星期日".equals(week)||"星期六".equals(week)){
			flag=false;
		}
		return flag;
	}
	public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
}
