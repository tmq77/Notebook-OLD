package cn.tmq.service.invoker.client.fallBackClass;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.tmq.service.invoker.client.ViewNoteClient;

@Component
public class ViewNoteClientFallback implements ViewNoteClient{

	@Override
	public Map<String, Object> view(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("status", "777");
		resultMap.put("message", "调用服务信息：系统异常，获取笔记失败");
		return resultMap;
	}

}
