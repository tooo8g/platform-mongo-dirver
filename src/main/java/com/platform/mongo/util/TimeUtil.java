package com.platform.mongo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class TimeUtil {

	private static SimpleDateFormat ymd_format1 = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static SimpleDateFormat ymd_format2 = new SimpleDateFormat(
			"yyyy/MM/dd");
	private static SimpleDateFormat ymd_format3 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat ymd_format4 = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");
	private static SimpleDateFormat ymd_format5 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
	private static SimpleDateFormat ymd_format6 = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm");

	private static SimpleDateFormat yy = new SimpleDateFormat("yy");
	private static SimpleDateFormat mm = new SimpleDateFormat("MM");

	private static Pattern p1 = Pattern
			.compile("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}");
	private static Pattern p2 = Pattern
			.compile("[0-9]{4}/[0-9]{1,2}/[0-9]{1,2}");
	private static Pattern p3 = Pattern
			.compile("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}");
	private static Pattern p4 = Pattern
			.compile("[0-9]{4}/[0-9]{1,2}/[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}");
	private static Pattern p5 = Pattern
			.compile("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}");
	private static Pattern p6 = Pattern
			.compile("[0-9]{4}/[0-9]{1,2}/[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}");

	public static Date parserTime(String date) {
		if (date != null && !date.equals("")) {
			Date r = null;
			try {
				if (p1.matcher(date).matches())
					r = new Date(ymd_format1.parse(date).getTime());
				else if (p2.matcher(date).matches())
					r = new Date(ymd_format2.parse(date).getTime());
				else if (p3.matcher(date).matches())
					r = new Date(ymd_format3.parse(date).getTime());
				else if (p4.matcher(date).matches())
					r = new Date(ymd_format4.parse(date).getTime());
				else if (p5.matcher(date).matches())
					r = new Date(ymd_format5.parse(date).getTime());
				else if (p6.matcher(date).matches())
					r = new Date(ymd_format6.parse(date).getTime());
			} catch (ParseException e) {
				r = new Date(0l);
			}
			return r;
		}

		return new Date(0l);
	}

	public static String parserStringYMD(Date date) {
		return ymd_format1.format(date);
	}

	public static Calendar getToday() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		return now;
	}

	public static Calendar getDay(int type, int value) {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.add(type, value);
		return now;
	}

	public static String YY(Date date) {
		return yy.format(date);
	}

	public static String MM(Date date) {
		return mm.format(date);
	}

	public static void main(String[] args) {
		System.out.println(TimeUtil.YY(new Date()));
		System.out.println(TimeUtil.MM(new Date()));
		System.out.println(Format.format5(100));
	}
}
