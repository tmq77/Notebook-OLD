package cn.tmq.myNotebook.commonUtils;

import cn.tmq.myNotebook.constants.Constants;

public class CommonUtils {
	/**
	 * null or empty string check.
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean checkNullOrEmpty(Object obj) {
		return obj == null || Constants.STR_BLANK.equals(obj);
	}
}
