/**
 * File Name 		: AppUserDM.java 
 * Description 		: This java class is used for Date & Time format.
 * Author 			: Mahaboob Subahan J
 * Date 			: 31-Dec-2014
 * 
 * This Software is Designed and Developed by Daemon Software and Services Pvt. Ltd India.
 * 
 * Version     Date           Modified By             Remarks
 * 0.1         31-Dec-2014    Mahaboob Subahan J      
 */
package com.ninox.focus.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
	public static String dateToString(Date date) {
		DateFormat dateFormatter;
		dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
		return dateFormatter.format(date);
	}
	
	public static String dateToStringTime(Date date) {
		DateFormat dateFormatter;
		dateFormatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
		return dateFormatter.format(date);
	}
	
	public static Date getcurrentdate() {
		Calendar cal = GregorianCalendar.getInstance();
		return cal.getTime();
	}
	
	public static String datetostringtimenew(Date dt1) {
		DateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(dt1);
	}
	public static String datetostringtimenandha(Date dt1) {
		DateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return formatter.format(dt1);
	}
	public static String datetostringtimenandha2(Date dt1) {
		DateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(dt1);
	}
}
