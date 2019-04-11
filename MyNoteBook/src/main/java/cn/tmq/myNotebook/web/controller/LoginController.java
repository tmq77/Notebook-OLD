package cn.tmq.myNotebook.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tmq.myNotebook.constants.Constants;
import cn.tmq.myNotebook.constants.Message;

/**
 * 注册登录处理器.
 * 2019年4月3日 21点58分
 * @author 陶敏麒
 * @version 1.0
 */
@Controller
public class LoginController extends BaseController {

	/**
	 * 登录页面跳转.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String redirectToLogin() {
		return Constants.VIEW_LOGIN;
	}

	/**
	 * 注册页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "/regist", method = RequestMethod.GET)
	public String redirectRegist() {
		return Constants.VIEW_REGIST;
	}

	/**
	 * 用户注册
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public String regist(HttpServletRequest request, Model model) {
		Map<String, String> resultMap = new HashMap<>();
		try {
			resultMap.put(Constants.AJAX_KEY, Constants.STR_NG);
			
			String account = request.getParameter("account");
			String password = request.getParameter("password");
			
			// 必须项验证
			if (!StringUtils.isEmpty(account) && !StringUtils.isEmpty(password)) {
				// 参数列
				Map<String, Object> paramMap = new HashMap<>();
				paramMap.put("account", account);
				paramMap.put("password", password);
				paramMap.put("level", "0");
				// TODO 远程调用失败后的处理
				Message<String> message = this.postForService("regist", paramMap);
				// 根据返回值的更新数据来判断成功与否
				return this.validateResult(message, model, Constants.VIEW_LOGIN, Constants.VIEW_REGIST, null);
			} else {
				model.addAttribute("error", "1");
				model.addAttribute("message", "必须项目不能为空");
				return Constants.VIEW_REGIST;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return this.exceptionHandler(model);
		}
	}

	/**
	 * 登录处理
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> login(HttpServletRequest request,Model model,HttpSession session) {
		Map<String, String> resultMap = new HashMap<>();
		try {
			String account = request.getParameter("account");
			String password = request.getParameter("password");
			// 必要验证
			if (!StringUtils.isEmpty(account) && !StringUtils.isEmpty(password)) {
				// 参数列
				Map<String, Object> paramMap = new HashMap<>();
				paramMap.put("account", account);
				paramMap.put("password", password);
				// 远程调用
				// 返回的值将会被转化为json数据处理
				Message<HashMap<String, Object>> message = this.postForService("login", paramMap);
				if ("200".equals(message.getStatus())) {
					session.setAttribute(Constants.SESSION_USER, message.getResult());
					resultMap.put("error", "0");
					resultMap.put("message", "登录成功,请稍后");
				} else if ("777".equals(message.getStatus())) {
					throw new Exception();
				} else {
					resultMap.put("error", "1");
					resultMap.put("message", "账号密码错误");
				}
				
				return resultMap;
			} else {
				resultMap.put("error", "1");
				resultMap.put("message", "必须项目不能为空");
				return resultMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("error", "1");
			resultMap.put("message", "系统异常");
			return resultMap;
		}
	}
}
