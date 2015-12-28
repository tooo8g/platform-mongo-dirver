package com.platform.mongo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

	private static SimpleDateFormat ymd_format = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static SimpleDateFormat ymd_format2 = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");
	private static SimpleDateFormat ymd_format3 = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static Date parserDate(String date) {
		if (date != null && !date.equals("")) {
			Date r = null;
			try {
				r = new Date(ymd_format.parse(date).getTime());
			} catch (ParseException e) {
				r = new Date(0l);
			}
			return r;
		}

		return null;
	}

	// public static Date now() {
	// return new Date(Calendar.getInstance().getTimeInMillis());
	// }
	//
	// public static String pointDay(int amount) {
	// Calendar now = Calendar.getInstance();
	// now.add(Calendar.DAY_OF_MONTH, -amount);
	// return ymd_format3.format(now.getTime()) + " 00:00:00";
	// }

	public static void main(String[] args) {
		// System.out.println(TimeUtil.pointDay(16));
		for (int i = 0; i < 100; i++) {
			System.out.println(((i * 1000) + 1) + "|" + ((i * 1000) + 1000));
		}
	}
}
