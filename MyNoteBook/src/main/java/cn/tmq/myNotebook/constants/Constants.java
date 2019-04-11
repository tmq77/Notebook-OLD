package cn.tmq.myNotebook.constants;

/**
 * 视图View字符串
 * 
 * @author 陶敏麒
 * @version 1.0
 */
public class Constants {
	
	/**
	 * 拦截器返回页面.
	 */
	public static final String INTERCEPTOR_VIEW = "/login";

	/**
	 * 登录页面.
	 */
	public static final String VIEW_LOGIN = "login";

	/**
	 * 注册页面.
	 */
	public static final String VIEW_REGIST = "regist";

	/**
	 * 个人页面.
	 */
	public static final String VIEW_HOME = "home";

	/**
	 * 查看笔记页面.
	 */
	public static final String VIEW_VIEW = "view";

	/**
	 * 主页面.
	 */
	public static final String VIEW_INDEX = "index";
	
	/**
	 * 信息页.
	 */
	public static final String VIEW_INFO = "info";

	/**
	 * ajax请求返回值的key.
	 */
	public static final String AJAX_KEY = "result";
	
	/**
	 * 自带Model的key.
	 */
	public static final String MODEL_KEY="model";

	/**
	 * 邮箱验证.
	 */
	public static final String EMAIL_CHECK = "^\\w+@(\\w+\\.)+\\w+$";

	/**
	 * 手机验证.
	 */
	public static final String PHONE_CHECK = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";

	/**
	 * 空字符串 “”.
	 */
	public static final String STR_BLANK = "";

	/**
	 * 字符串"ok".
	 */
	public static final String STR_OK = "ok";

	/**
	 * 字符串"ng".
	 */
	public static final String STR_NG = "ng";

	/**
	 * 字符串"error".
	 */
	public static final String STR_ERROR = "error";

	/**
	 * true.
	 */
	public static final boolean TRUE = true;

	/**
	 * false.
	 */
	public static final boolean FALSE = false;

	/**
	 * 0.
	 */
	public static final int INT_0 = 0;

	/**
	 * 插入更新失败信息.
	 */
	public static final String DBNGMSG = "数据更新失败。";

	/**
	 * 盐.
	 */
	public static final String SALT = "salt";

	/**
	 * 用户信息key： user
	 */
	public static final String SESSION_USER = "user";

	/**
	 * 用户信息key： user
	 */
	public static final String REQUEST_ACCOUNT = "account";

	public static final String STR_BACKSLASH = "/";
}
