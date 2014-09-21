package com.zd.lbsx.utils;

public class StringUtils {
	public static boolean isEmpty(String string) {
		if (string == null || string.equals("") || string.equals(null)) {
			return true;
		}
		return false; 
	}
}
