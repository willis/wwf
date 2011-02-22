package com.mpaike.core.util.date;

import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * <p>
 * Title: <font color=red></font> Description: Copyright: Copyright (c) 2002
 * Company: www.myniko.com
 * 
 * @author <font color=red>niko </font>
 * @version 1.0
 */
public final class DateTimeUtil {

	private static final String format1 = "yyyy-MM-dd HH:mm:ss";

	private static final String format2 = "yyyy-MM-dd";
	
	private static final String format3 = "yyyy/MM/dd";
	
	private static final String format4 = "yyyy-MM-dd'T'HH:mm:ss";

	private static final SimpleDateFormat dateFormat1 = new SimpleDateFormat(
			format1);

	private static final SimpleDateFormat dateFormat2 = new SimpleDateFormat(
			format2);

	private static final SimpleDateFormat dateFormat3 = new SimpleDateFormat(
			format3);
	
	private static final SimpleDateFormat dateFormat4 = new SimpleDateFormat(
			format4);

	public DateTimeUtil() {
	}

	
	public static Date getServerDate(String s) throws Exception {
		Date date = dateFormat4.parse(s);
    	return new Date(date.getTime()-date.getTimezoneOffset()*60000);
	}
	
	public static long getTimeStamp(String time) throws ParseException {
		return dateFormat2.parse(time).getTime();
	}

	public static Date getTimeStampDate(String time) {
		if (time != null && !time.equals("")) {
			try {
				return dateFormat2.parse(time);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else {
			return null;
		}
	}

	public static Date getTimeStampDate(String time, int type) {
		if (time == null || time.equals("")) {
			return null;
		} else {
			try {
				if (type == 1)
					return dateFormat2.parse(time);
				else
					return dateFormat1.parse(time);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}

	public static long getTimeStamp(String time, int format)
			throws ParseException {
		if (time == null | time.equals("")) {
			return 0;
		} else {
			if (format == 1) {
				return dateFormat1.parse(time).getTime();
			} else if (format == 2) {
				return dateFormat2.parse(time).getTime();
			} else {
				return dateFormat2.parse(time).getTime();
			}
		}
	}

	public static String getTime(long timeStamp) {
		return dateFormat2.format(new Date(timeStamp));
	}

	public static String getTime(long timeStamp, int format) {
		if (format == 1) {
			return dateFormat1.format(new Date(timeStamp));
		} else if (format == 2) {
			return dateFormat2.format(new Date(timeStamp));
		} else {
			return dateFormat2.format(new Date(timeStamp));
		}
	}

	public static long getTime(int days) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_YEAR, -(days));
		return now.getTimeInMillis();
	}

	public static void main(String[] argv) {
			boolean compareDay = DateTimeUtil.compareDay(DateTimeUtil.getDate("2009-12-12 07:14:04", "yyyy-MM-dd HH:mm:ss"),new Date(), 2);
//			getCurrentWeeHour(new Date());
			System.out.println(compareDay);

	}

	public static String formatTimestamp(Object date) {
		try {
			return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));

		} catch (Exception e) {
			return "";
		}

	}

	public static String formatTimestampDay(Object date) {
		try {
			return (new SimpleDateFormat("yyyy-MM-dd").format(date));

		} catch (Exception e) {
			return "";
		}

	}

	public static String timeZone(long time, long zone) {
		long hour = 3600000;
		if (zone == 1) {
			return formatTimestamp(time - 20 * hour);
		} else if (zone == 2) {
			return formatTimestamp(time - 19 * hour);
		} else if (zone == 3) {
			return formatTimestamp(time - 18 * hour);
		} else if (zone == 4) {
			return formatTimestamp(time - 17 * hour);
		} else if (zone == 5) {
			return formatTimestamp(time - 16 * hour);
		} else if (zone == 6) {
			return formatTimestamp(time - 15 * hour);
		} else if (zone == 7) {
			return formatTimestamp(time - 14 * hour);
		} else if (zone == 8) {
			return formatTimestamp(time - 13 * hour);
		} else if (zone == 9) {
			return formatTimestamp(time - 12 * hour);
		} else if (zone == 10) {
			return formatTimestamp(time - 11.5 * hour);
		} else if (zone == 11) {
			return formatTimestamp(time - 11 * hour);
		} else if (zone == 12) {
			return formatTimestamp(time - 10 * hour);
		} else if (zone == 13) {
			return formatTimestamp(time - 9 * hour);
		} else if (zone == 14) {
			return formatTimestamp(time - 8 * hour);
		} else if (zone == 15) {
			return formatTimestamp(time - 7 * hour);
		} else if (zone == 16) {
			return formatTimestamp(time - 6 * hour);
		} else if (zone == 17) {
			return formatTimestamp(time - 5 * hour);
		} else if (zone == 18) {
			return formatTimestamp(time - 4.5 * hour);
		} else if (zone == 19) {
			return formatTimestamp(time - 4 * hour);
		} else if (zone == 20) {
			return formatTimestamp(time - 3.5 * hour);
		} else if (zone == 21) {
			return formatTimestamp(time - 3 * hour);
		} else if (zone == 22) {
			return formatTimestamp(time - 2.5 * hour);
		} else if (zone == 23) {
			return formatTimestamp(time - 2.25 * hour);
		} else if (zone == 24) {
			return formatTimestamp(time - 2 * hour);
		} else if (zone == 25) {
			return formatTimestamp(time - 1.5 * hour);
		} else if (zone == 26) {
			return formatTimestamp(time - 1 * hour);
		} else if (zone == 27) {
			return formatTimestamp(time);
		} else if (zone == 28) {
			return formatTimestamp(time + 1 * hour);
		} else if (zone == 29) {
			return formatTimestamp(time + 1.5 * hour);
		} else if (zone == 30) {
			return formatTimestamp(time + 2 * hour);
		} else if (zone == 31) {
			return formatTimestamp(time + 3 * hour);
		} else if (zone == 32) {
			return formatTimestamp(time + 4 * hour);
		} else {
			return "";
		}
	}

	public static String timeZoneGMT(long time, long zone) {
		long hour = 3600000;
		if (zone == 1) {
			return formatTimestamp(time - 20 * hour) + "(GMT -12:00)";
		} else if (zone == 2) {
			return formatTimestamp(time - 19 * hour) + "(GMT -11:00)";
		} else if (zone == 3) {
			return formatTimestamp(time - 18 * hour) + "(GMT -10:00)";
		} else if (zone == 4) {
			return formatTimestamp(time - 17 * hour) + "(GMT -9:00)";
		} else if (zone == 5) {
			return formatTimestamp(time - 16 * hour) + "(GMT -8:00)";
		} else if (zone == 6) {
			return formatTimestamp(time - 15 * hour) + "(GMT -7:00)";
		} else if (zone == 7) {
			return formatTimestamp(time - 14 * hour) + "(GMT -6:00)";
		} else if (zone == 8) {
			return formatTimestamp(time - 13 * hour) + "(GMT -5:00)";
		} else if (zone == 9) {
			return formatTimestamp(time - 12 * hour) + "(GMT -4:00)";
		} else if (zone == 10) {
			return formatTimestamp(time - 11.5 * hour) + "(GMT -3:30)";
		} else if (zone == 11) {
			return formatTimestamp(time - 11 * hour) + "(GMT -3:00)";
		} else if (zone == 12) {
			return formatTimestamp(time - 10 * hour) + "(GMT -2:00)";
		} else if (zone == 13) {
			return formatTimestamp(time - 9 * hour) + "(GMT -1:00)";
		} else if (zone == 14) {
			return formatTimestamp(time - 8 * hour) + "(GMT)";
		} else if (zone == 15) {
			return formatTimestamp(time - 7 * hour) + "(GMT +01:00)";
		} else if (zone == 16) {
			return formatTimestamp(time - 6 * hour) + "(GMT +02:00)";
		} else if (zone == 17) {
			return formatTimestamp(time - 5 * hour) + "(GMT +03:00)";
		} else if (zone == 18) {
			return formatTimestamp(time - 4.5 * hour) + "(GMT +03:30)";
		} else if (zone == 19) {
			return formatTimestamp(time - 4 * hour) + "(GMT +04:00)";
		} else if (zone == 20) {
			return formatTimestamp(time - 3.5 * hour) + "(GMT +04:30)";
		} else if (zone == 21) {
			return formatTimestamp(time - 3 * hour) + "(GMT +05:00)";
		} else if (zone == 22) {
			return formatTimestamp(time - 2.5 * hour) + "(GMT +05:30)";
		} else if (zone == 23) {
			return formatTimestamp(time - 2.25 * hour) + "(GMT +05:45)";
		} else if (zone == 24) {
			return formatTimestamp(time - 2 * hour) + "(GMT +06:00)";
		} else if (zone == 25) {
			return formatTimestamp(time - 1.5 * hour) + "(GMT +06:30)";
		} else if (zone == 26) {
			return formatTimestamp(time - 1 * hour) + "(GMT +07:00)";
		} else if (zone == 27) {
			return formatTimestamp(time) + "(GMT +08:00)";
		} else if (zone == 28) {
			return formatTimestamp(time + 1 * hour) + "(GMT +09:00)";
		} else if (zone == 29) {
			return formatTimestamp(time + 1.5 * hour) + "(GMT +09:30)";
		} else if (zone == 30) {
			return formatTimestamp(time + 2 * hour) + "(GMT +10:00)";
		} else if (zone == 31) {
			return formatTimestamp(time + 3 * hour) + "(GMT +11:00)";
		} else if (zone == 32) {
			return formatTimestamp(time + 4 * hour) + "(GMT +12:00)";
		} else {
			return "";
		}
	}

	/**
	 * 将指定的日期字符串转化为日期对象
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @param format
	 *            日期格式
	 * 
	 * @return java.util.Date
	 */
	public static Date getDate(String dateStr, String format) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(format);

			Date date = df.parse(dateStr);

			return date;
		} catch (Exception e) {

		}

		return null;
	}

	/**
	 * 将日期转换为指定的字符串格式
	 * 
	 * @param date
	 *            日期
	 * @param fromat
	 *            日期格式
	 * 
	 * */
	public static String getDateStr(Date date, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);

		try {
			String dateStr = df.format(date);

			return dateStr;
		} catch (Exception ex) {
			return null;
		}

	}

	/**
	 * 将日期转换为指定的字符串格式
	 * 
	 * @param date
	 *            日期
	 * @param fromat
	 *            日期格式
	 * 
	 * */
	public static String getDateStr(Date date, String format, Locale locale) {
		SimpleDateFormat df = new SimpleDateFormat(format, locale);
		try {
			String dateStr = df.format(date);

			return dateStr;
		} catch (Exception ex) {
			return null;
		}

	}

	/**
	 * 
	 * @param d1
	 * @param d2
	 * @param negative
	 *            (是否允许为负天数)
	 * @return
	 */
	public long getDaysBetween(Calendar d1, Calendar d2, boolean negative) {
		if (!negative && d1.after(d2)) {
			java.util.Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		long days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
		int y2 = d2.get(Calendar.YEAR);
		if (d1.get(Calendar.YEAR) != y2) {
			d1 = (Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
				d1.add(Calendar.YEAR, 1);
			} while (d1.get(Calendar.YEAR) != y2);
		}
		return days;
	}

	public static boolean isTimeOut(Date date, long interval) {
		boolean flag = false;
		if (null == date)
			return flag;
		Date toDay = new Date();

		date.setTime(date.getTime() + interval);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		switch (dayOfWeek) {
		case 1:// SUNDAY+24H
			date.setTime(date.getTime() + 48 * 60 * 60 * 1000);
			break;
		case 7:// SATURDAY+48H
			date.setTime(date.getTime() + 24 * 60 * 60 * 1000);
			break;
		}

		if (toDay.after(date)) {
			flag = true;
		}
		return flag;
	}

	
    /**
     * Add date by amount
     * 
     * @param orignalDate
     * @param dayAmount
     * @return the date of dayAmount later or before
     */
    public static Date addDate(Date orignalDate, int dayAmount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(orignalDate);
        calendar.add(Calendar.DAY_OF_YEAR, dayAmount);
        return calendar.getTime();
    }
    
    public static String getDateToString(Date date) {
        String str = "";
        if (date != null) {
            str = dateFormat2.format(date);
        }
        return str;
    }
    
    public static String getDateToString2(Date date) {
    	String str = "";
    	if (date != null) {
    		str = dateFormat3.format(date);
    	}
    	return str;
    }
    
    public static String getDateToStringHms(Date date) {
    	String str = "";
    	if (date != null) {
    		str = dateFormat1.format(date);
    	}
    	return str;
    }
    
    /**
     * 判断是否为工作日 
     * @param calendar
     * @return
     */
    public static boolean isWorkday(Calendar calendar){  
        if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY  
                && calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){  
            return true;  
        }else{  
            return false;
        }  
    }  
    
    /**
     * 与当天日期比较的工作日数是否超过deadline的天数
     * @param date
     * @param deadline
     * @return
     */
    public static boolean compareCurrentWorkDay(Date date,int deadline){
    	return compareDay(date, new Date(), deadline);
    }
    
	/**
	 * 判断beforeDate与afterDate之间的工作天数是否在deadline天数之内
	 * @param beforeDate
	 * @param afterDate
	 * @param deadline
	 * @return
	 */
	public static boolean compareDay(Date beforeDate, Date afterDate,
			int deadline) {
		try {
			Calendar c1 = java.util.GregorianCalendar.getInstance();
			c1.setTime(beforeDate);
			c1.set(Calendar.HOUR_OF_DAY, 0);
			c1.set(Calendar.MINUTE, 0);
			c1.set(Calendar.SECOND, 1);
			c1.set(Calendar.MILLISECOND, 0);

			for (int day = 0; day != deadline;) {
				c1.add(c1.DATE, 1);
				//System.out.println(getTime(c1.getTimeInMillis()));
				if (isWorkday(c1)) {
					day++;
				}
			}
			

			Calendar c2 = java.util.GregorianCalendar.getInstance();
			c2.setTime(afterDate);
			c2.set(Calendar.HOUR_OF_DAY, 0);
			c2.set(Calendar.MINUTE, 0);
			c2.set(Calendar.SECOND, 1);
			c2.set(Calendar.MILLISECOND, 0);

			long cha = c2.getTimeInMillis() - c1.getTimeInMillis();
			//System.out.println(getTime(c1.getTimeInMillis()));
			//System.out.println(getTime(c2.getTimeInMillis()));
			if (cha > 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static String fastDateFormat(Date date) {
		Calendar calendar = Calendar.getInstance();
		StringBuilder out = new StringBuilder();
		calendar.setTime(date);
		out.append(calendar.get(1));
		out.append("-");
		out.append(calendar.get(2) + 1);
		out.append("-");
		out.append(calendar.get(5));
		out.append(" ");
		out.append(calendar.get(11));
		out.append(":");
		out.append(calendar.get(12));
		out.append(":");
		out.append(calendar.get(13));
		return out.toString();
	}
	
	  public static Date endOfDay(Date date)
	  {
	    if (date == null) return null;
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(11, 23);
	    cal.set(12, 59);
	    cal.set(13, 59);
	    cal.set(14, 999);
	    return cal.getTime();
	  }

	  public static Date startOfDay(Date date)
	  {
	    if (date == null) return null;
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(11, 0);
	    cal.set(12, 0);
	    cal.set(13, 0);
	    cal.set(14, 0);
	    return cal.getTime();
	  }
	  
		/**
		 * 转换运行时间长度,把运行时长的毫秒数转换成时分秒
		 * 
		 * @param runtime
		 *            运行时间
		 * @return 转换后的字符串(格式为00:00:00)
		 */
		public static String formatRuntime(long runtime) {
			long seconds = (runtime / 1000L) % 60L;
			long minutes = (runtime / 60000L) % 60L;
			long hours = (runtime / 0x36ee80L) % 24L;
			long days = runtime / 0x5265c00L;
			StringBuffer strBuf = new StringBuffer();
			if (days > 0L) {
				if (days < 10L) {
					strBuf.append('0');
				}
				strBuf.append(days);
				strBuf.append(':');
			}
			if (hours < 10L) {
				strBuf.append('0');
			}
			strBuf.append(hours);
			strBuf.append(':');
			if (minutes < 10L) {
				strBuf.append('0');
			}
			strBuf.append(minutes);
			strBuf.append(':');
			if (seconds < 10L) {
				strBuf.append('0');
			}
			strBuf.append(seconds);
			return strBuf.toString();
		}

	
}