package com.ninox.focus.view.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.apache.log4j.Logger;

public class DateUtil {
	private static Logger logger = Logger.getLogger(DateUtil.class);
	
	public static String datetostring(Date string) {
		DateFormat formatter;
		formatter = new SimpleDateFormat("dd-MMM-yyyy");
		return formatter.format(string);
	}
	
	public static String datetostringsimp(Date string) {
		DateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(string);
	}
	
	public static String stringdate(String dt1) {
		DateFormat formatter;
		formatter = new SimpleDateFormat("dd-MMM-yyyy");
		return formatter.format(dt1);
	}
	
	public static String datetostringsimple(Date dt1) {
		DateFormat formatter;
		formatter = new SimpleDateFormat("dd-MM-yyyy");
		return formatter.format(dt1);
	}
	
	public static String datetostringtime(Date dt1) {
		DateFormat formatter;
		formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
		return formatter.format(dt1);
	}
	
	public static String datetostringHrsMns(Date dt1) {
		DateFormat formatter;
		formatter = new SimpleDateFormat("HH:mm");
		return formatter.format(dt1);
	}
	
	public static String datetostringHrsMnsconc(Date dt1) {
		DateFormat formatter1, formatter2;
		formatter1 = new SimpleDateFormat("HH:mm");
		formatter2 = new SimpleDateFormat("HH:mm");
		return formatter1.format(dt1) + "" + formatter2.format(dt1);
	}
	
	public static String datetostringtimenew(Date dt1) {
		DateFormat formatter;
		formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		return formatter.format(dt1);
	}
	
	public static String dateformaterforstring(String date) {
		try {
			SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			Date tempDate = simpledateformat.parse(date);
			SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
			return outputDateFormat.format(tempDate);
		}
		catch (ParseException ex) {
			return "";
		}
	}
	
	public static String datetostringsimpletime(Date dt1) {
		DateFormat formatter;
		formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		return formatter.format(dt1);
	}
	
	public static Date getcurrentdate() {
		Calendar cal = GregorianCalendar.getInstance();
		return cal.getTime();
	}
	
	public static Date getcurrentmonth() {
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}
	
	public static Date addDays(Date value, int days) {
		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = sdf.format(value);
		Date parsedDate = null;
		try {
			parsedDate = sdf.parse(strDate);
		}
		catch (ParseException e) {
			logger.warn("addDays ", e);
		}
		Calendar now = Calendar.getInstance();
		now.setTime(parsedDate);
		now.add(Calendar.DAY_OF_MONTH, days);
		return (now.getTime());
	}
	
	public static String monthYear(Date dt1) {
		DateFormat formatter;
		formatter = new SimpleDateFormat("MMM-yyyy");
		return formatter.format(dt1);
	}
	
	public static String monthDateYear(Date dt1) {
		DateFormat formatter;
		formatter = new SimpleDateFormat("MM/dd/yy");
		return formatter.format(dt1);
	}
	
	public static String batchYear(Date dt1) {
		DateFormat formatter;
		formatter = new SimpleDateFormat("yyyy");
		return formatter.format(dt1);
	}
	public static String batchMnth(Date dt1) {
		DateFormat formatter;
		formatter = new SimpleDateFormat("MM");
		return formatter.format(dt1);
	}
	public static String batchDate(Date dt1) {
		DateFormat formatter;
		formatter = new SimpleDateFormat("dd");
		return formatter.format(dt1);
	}
}