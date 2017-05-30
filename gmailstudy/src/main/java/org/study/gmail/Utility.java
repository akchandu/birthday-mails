package org.study.gmail;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utility {

	public static void main(String[] args) {
		getTodaysMonth();
		getTodayDate();

	}
	
	public static String getTodaysMonth() {
		Calendar calendar = Calendar.getInstance();
		String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
		return month;
	}
	
	public static String getTodayDate() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM:d");
		String todayDate = sdf.format(date);
		return (todayDate);
	}

}
