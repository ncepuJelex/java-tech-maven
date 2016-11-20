package com.jel.tech.common.date;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.validator.routines.DateValidator;

/**
 * 日期工具类
 * @author zhenhua
 *  2016-11-15
 */
public class DateUtils {

	private static final DateValidator validator = DateValidator.getInstance();
	
	private DateUtils() {}
	
	public static String format(Date d) {
		return validator.format(d);
	}
	
	public static String format(Date d,String pattern) {
		return validator.format(d, pattern);
	}
	
	public static String format(Date d,Locale locale) {
		return validator.format(d, locale);
	}
	
	public static String format(Date d,Locale locale,TimeZone timeZone) {
		return validator.format(d,locale,timeZone);
	}
	
	public static String format(Date d,TimeZone timeZone) {
		return validator.format(d,timeZone);
	}
	
	public static String format(Date d,String pattern,TimeZone timeZone) {
		return validator.format(d,pattern,timeZone);
	}
	
	public static String format(Date d,String pattern,Locale locale) {
		return validator.format(d,pattern,locale);
	}
	
	public static String format(Date d,String pattern,Locale locale,TimeZone timeZone) {
		return validator.format(d,pattern,locale,timeZone);
	}
	
	public static Date parse(String dateStr) {
		return validator.validate(dateStr);
	}
	
	public static Date parse(String dateStr,Locale locale) {
		return validator.validate(dateStr,locale);
	}
	
	public static Date parse(String dateStr,String pattern) {
		return validator.validate(dateStr,pattern);
	}
	
	public static Date parse(String dateStr,TimeZone timeZone) {
		return validator.validate(dateStr,timeZone);
	}
	
	public static Date parse(String dateStr,String pattern,Locale locale) {
		return validator.validate(dateStr,pattern,locale);
	}
	
	public static Date parse(String dateStr,String pattern,TimeZone timeZone) {
		return validator.validate(dateStr,pattern,timeZone);
	}
	
	public static Date parse(String dateStr,String pattern,Locale locale,TimeZone timeZone) {
		return validator.validate(dateStr,pattern,locale,timeZone);
	}
	/*
	 * 默认yyyy-MM-dd格式的正确日期为有效
	 */
	public static boolean isValid(String dateStr) {
		return validator.isValid(dateStr);
	}
	
	public static boolean isValid(String dateStr,Locale locale) {
		return validator.isValid(dateStr,locale);
	}
	
	public static boolean isValid(String dateStr,String pattern) {
		return validator.isValid(dateStr,pattern);
	}
	
	public static boolean isValid(String dateStr,String pattern,Locale locale) {
		return validator.isValid(dateStr,pattern,locale);
	}
	
	public static int compareYear(Date d1,Date d2) {
		return compareYear(d1, d2, null);
	}
	
	public static int compareYear(Date d1,Date d2,TimeZone timeZone) {
		return validator.compareYears(d1, d2, timeZone);
	}
	public static int compareMonth(Date d1,Date d2,TimeZone timeZone) {
		return validator.compareMonths(d1, d1, timeZone);
	}
	public static int compareMonth(Date d1,Date d2) {
		return validator.compareMonths(d1, d1, null);
	}
	public static int compareDate(Date d1,Date d2,TimeZone timeZone) {
		return validator.compareDates(d1, d1, timeZone);
	}
	public static int compareDate(Date d1,Date d2) {
		return validator.compareDates(d1, d1, null);
	}
	public static int compareWeek(Date d1,Date d2,TimeZone timeZone) {
		return validator.compareWeeks(d1, d1, timeZone);
	}
	public static int compareWeek(Date d1,Date d2) {
		return compareWeek(d1, d1, null);
	}
	public static int compareQuarter(Date d1,Date d2,TimeZone timeZone) {
		return validator.compareQuarters(d1, d1, timeZone);
	}
	public static int compareQuarter(Date d1,Date d2) {
		return compareQuarter(d1, d1, null);
	}
	public static java.util.Date truncateYear(java.util.Date date) {
		return org.apache.commons.lang3.time.DateUtils.truncate(date, 1);
	}

	public static java.util.Date truncateMonth(java.util.Date date) {
		return org.apache.commons.lang3.time.DateUtils.truncate(date, 2);
	}

	public static java.util.Date truncateDay(java.util.Date date) {
		return org.apache.commons.lang3.time.DateUtils.truncate(date, 5);
	}

	public static java.util.Date truncateHour(java.util.Date date) {
		return org.apache.commons.lang3.time.DateUtils.truncate(date, 10);
	}

	public static java.util.Date truncateMinute(java.util.Date date) {
		return org.apache.commons.lang3.time.DateUtils.truncate(date, 12);
	}

	public static java.util.Date truncateSecond(java.util.Date date) {
		return org.apache.commons.lang3.time.DateUtils.truncate(date, 13);
	}
	
	public static java.util.Date addDays(java.util.Date date, int days) {
		return org.apache.commons.lang3.time.DateUtils.addDays(date, days);
	}
}
