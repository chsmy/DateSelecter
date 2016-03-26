package dateselecter.chs.com.dateselecter.util;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author ysq
 * 关于已过去时间的帮助类
 *
 */
public class GetTimeUtil {

	public static String getDistanceTime(long time2) {
		Date now = new Date();
		long day = 0;// 天数
		long hour = 0;// 小时
		long min = 0;// 分钟
		long sec = 0;// 秒
		try {
			long time1 = now.getTime();
			System.out.println("当前时间："+time1);
			time2 = time2 * 1000l;
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			day = diff / (24 * 60 * 60 * 1000);
			hour = (diff / (60 * 60 * 1000));
			min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String rs = "";
		if (hour == 0) {
			rs = min + "分钟前";
			System.out.println(rs);
			return rs;
		}
		if (day == 0 && hour <= 4) {
			rs = hour + "小时前";
			System.out.println(rs);
			return rs;
		}
		SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
		String d = format.format(time2);
		Date date = null;
		try {
			date = format.parse(d);// 把字符类型的转换成日期类型的
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (now.getDate() - date.getDate() == 0) {// 当前时间和时间戳转换来的时间的天数对比
			DateFormat df2 = new SimpleDateFormat("HH:mm");
			rs = "今天  " + df2.format(time2);
			System.out.println(rs);
			return rs;
		} else if (now.getDate() - date.getDate() == 1) {
			DateFormat df2 = new SimpleDateFormat("HH:mm");
			rs = "昨天  " + df2.format(time2);
			System.out.println(rs);
			return rs;
		} else {
			DateFormat df2 = new SimpleDateFormat("MM-dd HH:mm");
			rs = df2.format(time2);
			System.out.println(rs);
			return rs;
		}
	}
	//long to String 年月日 小时、分、秒
	public static String long2String(long time) {
        String brith_StrTime = null;
        SimpleDateFormat sdf = null;
        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        brith_StrTime = sdf.format(new Date(time * 1000L));
//        System.out.println(brith_StrTime);
        return brith_StrTime;
	}

	//long to String 年月日 小时、分
	public static String long2StringHHmm(long time) {
		String brith_StrTime = null;
		SimpleDateFormat sdf = null;
		sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		brith_StrTime = sdf.format(new Date(time * 1000L));
//        System.out.println(brith_StrTime);
		return brith_StrTime;
	}

	//long to String 小时、分
	public static String long2HHmm(long time) {
		String brith_StrTime = null;
		SimpleDateFormat sdf = null;
		sdf = new SimpleDateFormat("HH:mm");
		brith_StrTime = sdf.format(new Date(time * 1000L));
//        System.out.println(brith_StrTime);
		return brith_StrTime;
	}
	
	//获取 年月日、小时
	public static String getYMDHTime(long time) {
        String brith_StrTime = null;
        SimpleDateFormat sdf = null;
        sdf = new SimpleDateFormat("yyyy/MM/dd HH");
        brith_StrTime = sdf.format(new Date(time * 1000L));
        return brith_StrTime;
	}
	//获取 年月日
	public static String getYMDTime1(long time) {
        String brith_StrTime = null;
        SimpleDateFormat sdf = null;
        sdf = new SimpleDateFormat("yyyy/MM/dd");
        brith_StrTime = sdf.format(new Date(time));
        return brith_StrTime;
	}
	//获取 年月日
	public static String getYMDTime(long time) {
        String brith_StrTime = null;
        SimpleDateFormat sdf = null;
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        brith_StrTime = sdf.format(new Date(time));
        return brith_StrTime;
	}
	//获取 年月日
	public static String getYMDTimezh(long time) {
        String brith_StrTime = null;
        SimpleDateFormat sdf = null;
        sdf = new SimpleDateFormat("yyyy年MM月dd日");
        brith_StrTime = sdf.format(new Date(time));
		String[] weeks =
				{ "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		Calendar calendar = Calendar.getInstance();
		int i = calendar.get(Calendar.DAY_OF_WEEK);
		String week = weeks[i-1];
        return brith_StrTime+" "+week;
	}
	//String换成时间戳提交
	public static String ymd2Timestamp(String dateString) throws ParseException {
		String timeStamp = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date d;
		try{
			d = sdf.parse(dateString);
			long l = d.getTime()/1000;
			timeStamp = String.valueOf(l);
		} catch(ParseException e){
			e.printStackTrace();
		}
		return timeStamp;
	}
	//String换成时间戳提交
	public static String ymd2Timestamp1(String dateString) throws ParseException {
		String timeStamp = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d;
		try{
			d = sdf.parse(dateString);
			long l = d.getTime()/1000;
			timeStamp = String.valueOf(l);
		} catch(ParseException e){
			e.printStackTrace();
		}
		return timeStamp;
	}
	//字符串转换成时间戳提交-只转换年
	public static long string2Timestamp(String dateString) throws ParseException {
		Date date1 = new SimpleDateFormat("yyyy")
				.parse(dateString);
		long temp = date1.getTime()/1000;// JAVA的时间戳长度是13位,除以1000和php一样
		return temp;
	}
	//String换成时间戳提交
	public static String ymdhm2Timestamp(String dateString) throws ParseException {
		String timeStamp = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH/mm");
		Date d;
		try{
			d = sdf.parse(dateString);
			long l = d.getTime()/1000;
			timeStamp = String.valueOf(l);
		} catch(ParseException e){
			e.printStackTrace();
		}
		return timeStamp;
	}
	//String换成时间戳提交
	public static String ymdhms2Timestamp(String dateString) throws ParseException {
		String timeStamp = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH/mm/ss");
		Date d;
		try{
			d = sdf.parse(dateString);
			long l = d.getTime()/1000;
			timeStamp = String.valueOf(l);
		} catch(ParseException e){
			e.printStackTrace();
		}
		return timeStamp;
	}

	//获取几小时，几分钟后的时间
	public static String getDate(int hour,int min){
		Date d = new Date();
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.HOUR, now.get(Calendar.HOUR) + hour);
		now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + min);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH/mm");

		return sdf.format(now.getTime());
	}
	/**
	 * 根据年月获得 这个月总共有几天
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDay(int year, int month) {
		int day = 30;
		boolean flag = false;
		switch (year % 4) {
			case 0:
				flag = true;
				break;
			default:
				flag = false;
				break;
		}
		switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				day = 31;
				break;
			case 2:
				day = flag ? 29 : 28;
				break;
			default:
				day = 30;
				break;
		}
		return day;
	}

	/**
	 *
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static String timeStamp2Date(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		return sdf.format(new Date(Long.valueOf(dateString)*1000));
	}

}