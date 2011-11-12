/**
 * COPYRIGHT. Harry Wu 2011. ALL RIGHTS RESERVED.
 * Project: mp3fenge
 * Author: Harry Wu <harrywu304@gmail.com>
 * Created On: Jun 19, 2011 2:23:13 PM
 *
 */
package com.google.code.mp3fenge.util;

import java.io.UnsupportedEncodingException;

public class StringUtil {

	/**
	 * 转换字符串编码
	 * @param content
	 * @param oldEncode
	 * @return
	 */
	public static String convertEncode(String content,String oldEncode){
		try {
			return new String(content.getBytes(oldEncode),"gbk");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	/**
	 * 判断字符串是否为空字符。
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isBlank(String value) {
		boolean ret = false;
		if (value != null && value.equals("")) {
			ret = true;
		}
		return ret;
	}

	/**
	 * 判断字符串是否为null。
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNull(String value) {
		return value == null ? true : false;
	}
	/**
	 * 判断字符串是否为空字符串或者null。
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNullOrBlank(String value) {
		return isNull(value) || isBlank(value);
	}
	
}
