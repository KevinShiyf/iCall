package com.icall.free.util;

import android.content.Context;

import com.icall.free.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author wanghao 2015-4-14 日期转换工具
 *
 */
public class TimesUtils {

	private static final long HOUR = 60 * 60;
	private static final long DAY = 24 * 60 * 60 * 1000;
	private static Calendar mCalendar = Calendar.getInstance();

	public static String getDialDate(Context context, long time) {
		String ret = "";
		int curMonth = mCalendar.get(Calendar.MONTH) + 1;
		int curDay = mCalendar.get(Calendar.DAY_OF_MONTH);
		mCalendar.setTimeInMillis(time);
		int month = mCalendar.get(Calendar.MONTH) + 1;
		int day = mCalendar.get(Calendar.DAY_OF_MONTH);
		if (curMonth != month) {
			ret = month + "-" + day;
		} else {
			if (curDay == day + 1) {
				ret = context.getResources().getString(R.string.yesterday);
			} else if (curDay == day) {
				ret = getDate(context, time, 0);
			} else {
				ret = month + "-" + day;
			}
		}

		return ret;
	}

	public static String getDate(Context context, long time, int type) { // 0-通话记录
																			// 1-通话详情
		// Log.e(TAG,"time:" + time);
		String ret = "";
		int curMonth = mCalendar.get(Calendar.MONTH) + 1;
		int curDay = mCalendar.get(Calendar.DAY_OF_MONTH);
		mCalendar.setTimeInMillis(time);
		int month = mCalendar.get(Calendar.MONTH) + 1;
		int day = mCalendar.get(Calendar.DAY_OF_MONTH);
		int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
		int minute = mCalendar.get(Calendar.MINUTE);
		if (curMonth != month) {
			ret = month + "-" + (day > 9 ? day : ("0" + day));
		} else {
			if (curDay == day + 1) {
				ret = context.getResources().getString(R.string.yesterday) + hour + ":"
						+ (minute > 9 ? minute : ("0" + minute));
			} else if (curDay == day) {
				if (type == 0) {
					ret = hour + ":" + (minute > 9 ? minute : ("0" + minute));
				} else {
					ret = context.getResources().getString(R.string.today) + hour + ":"
							+ (minute > 9 ? minute : ("0" + minute));
				}
			} else {
				ret = month + "-" + (day > 9 ? day : ("0" + day));
			}
		}

		return ret;
	}

//	public static String getDuration(Context context, long time) {
//		String ret = "";
//		long hour;
//		long minute;
//		long second;
//		hour = time / HOUR;
//		if (hour != 0) {
//			long tmp = time - hour * time;
//			minute = tmp / 60;
//			if (minute != 0) {
//				second = tmp - minute * 60;
//				ret = hour + context.getResources().getString(R.string.hour) + minute
//						+ context.getResources().getString(R.string.minute) + second
//						+ context.getResources().getString(R.string.second);
//			} else {
//				second = tmp;
//				ret = hour + context.getResources().getString(R.string.hour) + second
//						+ context.getResources().getString(R.string.second);
//			}
//		} else {
//			minute = time / 60;
//			if (minute != 0) {
//				second = time - minute * 60;
//				ret = minute + context.getResources().getString(R.string.minute) + second
//						+ context.getResources().getString(R.string.second);
//			} else {
//				second = time;
//				ret = second + context.getResources().getString(R.string.second);
//			}
//		}
//
//		return ret;
//	}

	/**
	 * @since 2015-10-27
	 * @author wanghao
	 * @param context
	 * @param time
	 * @return 返回通话详情时间title
	 */
	public static String getDateTitle(Context context, long time) {
		int cur_year = mCalendar.get(Calendar.YEAR);
		int cur_month = mCalendar.get(Calendar.MONTH) + 1;
		int cur_day = mCalendar.get(Calendar.DAY_OF_MONTH);

		mCalendar.set(cur_year, cur_month - 1, cur_day, 0, 0, 0);
		long cur_min_time = mCalendar.getTimeInMillis();
		long yeastoday_min_time = cur_min_time - DAY;

		if (time >= cur_min_time) {
			return "今天";
		} else if (time < cur_min_time && time >= yeastoday_min_time) {
			return "昨天";
		} else {
			return getSimpleFormat("yyyy/MM/dd").format(time);
		}
	}

	public static SimpleDateFormat getSimpleFormat(String formats) {
		return new SimpleDateFormat(formats);
	}

	public static String getChineseTimeSimpleFormat(long time) {
		mCalendar.setTimeInMillis(time);
		int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
		int minute = mCalendar.get(Calendar.MINUTE);
		String hour_str = hour > 9 ? ("" + hour) : ("0" + hour);
		String minute_str = minute > 9 ? ("" + minute) : ("0" + minute);
		return (hour_str + ":" + minute_str);
	}

	/**
	 * @since 2015-10-27
	 * @author wanghao
	 * @param time1
	 * @param time2
	 *            判断两个日期是否为同一天
	 * @return
	 */
	public static boolean is_same_dates(long time1, long time2) {
		SimpleDateFormat format = getSimpleFormat("yyyy/MM/dd");
		String date_str1 = format.format(new Date(time1));
		String date_str2 = format.format(new Date(time2));
		if (date_str1.equals(date_str2)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 计算传的日期离当天是今天、昨天、当年还是去年
	 * 
	 * @param date
	 * @return 0x999 去年，0 当天，-1 昨天，其它值是当年的其它天
	 */
	public static int countDate(long date) {
		SimpleDateFormat mathe = getSimpleFormat("yyyy-MM-dd");
		// 得到毫秒数
		String currentData = mathe.format(date);
		// 得到毫秒数
		long dayCount = 0;
		try {
			long timeEnd = mathe.parse(currentData).getTime();
			long timeStart = mathe.parse(mathe.format(new Date())).getTime();
			// 两个日期想减得到天数
			dayCount = ((timeEnd - timeStart) / (24 * 3600 * 1000));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (dayCount > -7) {
			return (int) dayCount;
		} else {
			Date curr = new Date(System.currentTimeMillis());
			Date end = new Date(date);
			mCalendar.setTime(curr);
			int cyear = mCalendar.get(Calendar.YEAR);
			mCalendar.setTime(end);
			int eyear = mCalendar.get(Calendar.YEAR);
			if (cyear == eyear) {
				return (int) dayCount;
			} else {
				return 0x999;
			}
		}




	}

	/**
	 * 对服务器返回的时间格式进行转换
	 * 
	 * @param date
	 * @return
	 */
	public static String dateShow(String date) {
		if (date == null && !"".equals(date))
			return "";
		SimpleDateFormat sdf = getSimpleFormat("yyyy/MM/dd HH:mm:ss");
		SimpleDateFormat newFormat = getSimpleFormat("yyyy/MM/dd HH:mm");
		Date newDate = null;
		try {
			newDate = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (newDate == null)
			return "";
		return newFormat.format(newDate);
	}


	public static String getCurrentTime() {
		SimpleDateFormat sdf = getSimpleFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}
}
