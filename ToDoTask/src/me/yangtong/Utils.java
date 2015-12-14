package me.yangtong;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {

	/**
	 * can't be instantiated
	 */
	private Utils(){
		
	}
	
	/**
	 *  get the string want to display
	 */
	public static String getDisplayDate(int year, int monthOfYear,int dayOfMonth){
		return ""+year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
	}
	
	public static String getDateByMilli(long milliseconds){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.CHINA);
		Date date = new Date(milliseconds);
		return format.format(date);
	}
	
	
	public static long getMilloByDisplayDate(String displayDate){
		String[] strings = displayDate.split("-");
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		c.set(Integer.parseInt(strings[0]), Integer.parseInt(strings[1])-1, Integer.parseInt(strings[2]));
		return c.getTimeInMillis();
	}
	/**
	 * get the date of today (yyyy-MM-dd)
	 * @return
	 */
	public static String getTodayDate(){
		return getDateByMilli(System.currentTimeMillis());
	}
	
	public static String getNowTime(){
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		c.setTimeInMillis(System.currentTimeMillis());
		return c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
	}
	
	/**
	 * get the date of tomorrow (yyyy-MM-dd)
	 * @return
	 */
	public static String getTomorrowDate(){
		return getDateByMilli(System.currentTimeMillis()+24*60*60*1000);
	}
	
	
}
