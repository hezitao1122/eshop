package com.eshop.inventory.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/** 
 * 说明：日期处理
 * @version
 */
public class DateUtil {

	private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
	private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");
	private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat sdfTimes = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * 获取YYYY格式
	 * @return
	 */
	public static String getSdfTimes() {
		return sdfTimes.format(new Date());
	}
	
	/**
	 * 获取YYYY格式
	 * @return
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式
	 * @return
	 */
	public static String getDay() {
		return sdfDay.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式
	 * @return
	 */
	public static Date getDay(Date date) {

		try {
			date = sdfDay.parse(sdfDay.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date ;
	}

	/**
	 * 获取YYYYMMDD格式
	 * @return
	 */
	public static String getDays(){
		return sdfDays.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 * @return
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}

	/**
	* @Title: compareDate
	* @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
	* @param s
	* @param e
	* @return boolean  
	* @throws
	* @author fh
	 */
	public static boolean compareDate(String s, String e) {
		if(fomatDate(s)==null||fomatDate(e)==null){
			return false;
		}
		return fomatDate(s).getTime() >=fomatDate(e).getTime();
	}

	/**
	 * 格式化日期
	 * @return
	 */
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 格式化日期
	 * @param date
	 * @return
	 */
	public static Date fomatDateToSecond(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 校验日期是否合法
	 * @return
	 */
	public static boolean isValidDate(String s) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}
	
	/**
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int getDiffYear(String startTime,String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			//long aa=0;
			int years=(int) (((fmt.parse(endTime).getTime()-fmt.parse(startTime).getTime())/ (1000 * 60 * 60 * 24))/365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}
	 
	/**
     * <li>功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long 
     * @author zeryts
     */
    public static long getDaySub(String beginDateStr,String endDateStr){
        long day=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;
        
            try {
				beginDate = format.parse(beginDateStr);
				endDate= format.parse(endDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
            //System.out.println("相隔的天数="+day);
      
        return day;
    }
    
    /**
     * 得到n天之后的日期
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);
        
        return dateStr;
    }

    public static String getAfterDayDate(Date date,String days) {
    	int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.setTime(date);
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		date = canlendar.getTime();
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);
        return dateStr;
    }


    /**
     * 得到n天之后是周几
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
    	int daysInt = Integer.parseInt(days);
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);
        return dateStr;
    }

	/**
	 * 时间显示规则
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static String getViewTime(Long startTime, Long endTime) {
		String viewTime = "";
		Long timeDifference = endTime - startTime;
		Long day = timeDifference / (24 * 60 * 60 * 1000);
		Long hour = timeDifference / 1000 / 60 / 60;
		Long minutes = timeDifference / 1000 / 60;
		//判断
		if(minutes >= 1 && minutes < 60){
			viewTime = minutes + "分钟前";
			//1天＜当前时间-发布时间＞1小时，显示：X小时
		} else if (hour < 24 && hour >=1) {
			viewTime = hour + "小时前";
			//30天＜当前时间-发布时间＞1天，显示：X天
		} else if (day >= 1 && day <= 30 ) {
			viewTime = day +"天前";
		} else if(minutes < 1){
			viewTime = "刚刚";
		}else {
			//当前时间-发布时间＞30天，显示30+天
			viewTime = "30+天前";
		}
		return viewTime;
	}

	/**
	 * 获取当天零点零分零秒
	 * @return
	 */
	public static Date getDayZero(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date zero = calendar.getTime();
		return zero;
	}

	public static Date getAWeekLater(Date date ){
		Calendar c = Calendar.getInstance();
		//七天后
		c.setTime(date);
		c.add(Calendar.DATE, + 7);
		Date day = c.getTime();
		return day;
	}
	/**
	 * 获取n年日期
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date getYearLater(Date date,Integer n){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, n);
		date = calendar.getTime();
		return date;
	}


	/**
	 * 获取两个日期之间的差值
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static String getDayTimeDiff(Date startDate,Date endDate){
		// 获得两个时间的毫秒时间差异
		Long mm = endDate.getTime() - startDate.getTime();
		if(mm <= 0){
			return "剩余"+0+"分钟";
		}
		long nd =  24 * 60 * 60*1000;
		long nh =  60 * 60 * 1000;
		long nm =  60*1000;
		// 计算差多少天
		long day = mm / nd;
		// 计算差多少小时
		long hour = mm % nd / nh;
		// 计算差多少分钟
		long min = mm % nd % nh / nm;

		if(day !=0 ){
			return "剩余"+day+"天";
		}else if(hour !=0){
			return "剩余"+hour+"小时";
		}else {
			return "剩余"+min+"分钟";
		}
	}
}