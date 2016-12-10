package com.haoqu.meiquan.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 时间戳转换成字符串
 * @author apple
 *
 */
public class DateTools {
	private static SimpleDateFormat sf;
	/**
	 * 将时间戳转成字符串*/
	public static String getDateToString(Long time){
		Date d = new Date(time);
		sf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		return sf.format(d);
	}
	/**
	 *  将字符串转为时间戳 */
	public static long getStringTODate(String time){
		sf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date d = new Date();
		try {
			d = sf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d.getTime();
	}

}
