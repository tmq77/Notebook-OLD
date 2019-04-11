package cn.tmq.service.notebook.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 根据时间(毫秒级)生成数据库主键
 * @author 陶敏麒
 *
 */
public class IdGeneratorUtil {
	/**
	 * 私有化构造方法：工具类不需要实例.
	 */
	private IdGeneratorUtil() {}
	
	/**
	 * 因为用户id唯一，所以使用用户id作为后缀防止重复.
	 * @param id
	 * @return
	 */
	public static String generateId (String id) {
		// 生成时间数据
		String timeId = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhmmssSSS"));
		return timeId + id;
	}
}
