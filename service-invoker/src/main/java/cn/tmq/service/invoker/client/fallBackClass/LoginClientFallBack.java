package cn.tmq.service.invoker.client.fallBackClass;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.tmq.service.invoker.client.LoginClient;

@Component
public class LoginClientFallBack implements LoginClient {

	@Override
	public Map<String, Object> login(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("status", "777");
		resultMap.put("message", "系统异常,登录失败");
		return resultMap;
	}

	@Override
	public Map<String, Object> regist(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("status", "777");
		resultMap.put("message", "系统异常,注册失败");
		return resultMap;
	}

}
