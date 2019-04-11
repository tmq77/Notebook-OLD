package cn.tmq.service.invoker.client.fallBackClass;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.tmq.service.invoker.client.CreateNoteClient;

@Component
public class CreateNoteClientFallBack implements CreateNoteClient{

	@Override
	public Map<String, Object> notes(Map<String, String> paramMap) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("status", "777");
		resultMap.put("message", "系统异常,获取笔记列表失败");
		return resultMap;
	}
	
	@Override
	public Map<String, Object> note(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("status", "777");
		resultMap.put("message", "系统异常,新建笔记失败");
		return resultMap;
	}
}
