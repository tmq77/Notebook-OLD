package cn.tmq.service.invoker.client.fallBackClass;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.tmq.service.invoker.client.LoginClient;

@Component
public class LoginClientFallback implements LoginClient {

	private static final String ERROR_CODE = "777";
	
	@Override
	public Map<String, Object> login(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("status", ERROR_CODE);
		resultMap.put("message", "调用服务信息：系统异常,登录失败");
		return resultMap;
	}

	@Override
	public Map<String, Object> regist(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("status", ERROR_CODE);
		resultMap.put("message", "调用服务信息：系统异常,注册失败");
		return resultMap;
	}

}
