package cn.tmq.service.login.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.tmq.service.login.dto.LoginDto;
import cn.tmq.service.login.entity.MUsers;
import cn.tmq.service.login.service.RegistService;

@RestController
public class RegistController {

	@Value("${server.port}")
	private String port;
	@Value("${spring.application.name}")
	private String name;

	@Autowired
	private RegistService service;

	/**
	 * 注册功能
	 * 因为@RequestBody注解,框架会自动将参数映射到对应的类中
	 * 因为是@RestController，所以这里返回的值已经是json了,无需刻意转化为json,否则特殊符号比如[""]会被两次转义--> [\\"]会导致最终解析失败
	 * @param loginDto
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> regist(@RequestBody LoginDto loginDto, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		// 707:失败，200:成功, 700:必须项为空
		resultMap.put("status", "707");
		try {
			// 表明是哪个客户端在提供服务
			resultMap.put("message", this.name + this.port);
			if (StringUtils.isEmpty(loginDto.getAccount()) || StringUtils.isEmpty(loginDto.getPassword())
					|| StringUtils.isEmpty(loginDto.getLevel())) {
				resultMap.put("status", "700");
			} else {
				// 注册用户,插入用户表
				// TODO 用户详细
				MUsers user = new MUsers();
				BeanUtils.copyProperties(loginDto, user);
				int userNum = this.service.registUserInfo(user);
				resultMap.put("status", "200");
				resultMap.put("result", String.valueOf(userNum));
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 由于在调用者处使用了restTemplate调用的服务,这里出错将不会触发feign的回退
			// throw new Exception("注册服务异常"); 
			resultMap.put("status", "777");
			resultMap.put("message", "数据库处理异常");
		}
		return resultMap;
	}
}
