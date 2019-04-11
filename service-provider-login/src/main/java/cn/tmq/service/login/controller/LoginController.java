package cn.tmq.service.login.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.tmq.service.login.entity.MUsers;
import cn.tmq.service.login.entity.custom.MUserBean;
import cn.tmq.service.login.service.LoginService;

@RestController
public class LoginController {

	@Autowired
	private LoginService service;

	/**
	 * 登录服务
	 * @param param
	 * @return
	 * @throws Exception 抛出的异常会被hystrix框架认为需要执行回退，会自动执行回退方法
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> login(@RequestBody Map<String, String> paramMap) throws Exception {
		try {
			Map<String, Object> resultMap = new HashMap<>();
			// 必要项目为空,不予处理
			if (StringUtils.isEmpty(paramMap.get("account")) || StringUtils.isEmpty(paramMap.get("password"))) {
				resultMap.put("status", "700");
				resultMap.put("message", "必须项目为空");
				return resultMap;
			}

			MUsers user = new MUsers();
			user.setAccount(paramMap.get("account"));
			MUserBean loginUserInfo = this.service.selectUserInfo(user);

			if (paramMap.get("password").equals(loginUserInfo.getPassword())) {
				// 验证成功
				resultMap.put("status", "200");
				resultMap.put("result", loginUserInfo);
				return resultMap;
			} else {
				// 验证失败
				resultMap.put("status", "701");
				resultMap.put("message", "验证失败");
				return resultMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 此处抛出异常会被服务调用者接收然后触发回退
			throw new Exception("登录服务异常");
		}
	}
}
