package cn.tmq.myNotebook.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.tmq.myNotebook.constants.Constants;
import cn.tmq.myNotebook.constants.Message;

/**
 * 基础处理器类：用于各种处理器的继承
 * 提供远程调用功能、异常信息也返回功能、消息体验证功能
 * 2019年4月3日 21点23分
 * @author 陶敏麒
 * @version 1.0
 */
public class BaseController {
	// 远程调用地址
	@Value("${remote.url}")
	private String remoteUrl;
	
	/**
	 * 带负载均衡的远程调用类对象
	 */
	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * 泛型方法：根据提供的调用模块的功能和参数进行远程调用
	 * @param restFunc 需要调用的服务功能
	 * @param paramMap 传递的参数
	 * @return 远程服务返回值
	 * @throws Exception 远程调用异常/类型转换异常等
	 */
	protected <T> Message<T> postForService(String restFunc, Map<String, Object> paramMap) throws Exception{
		// 消息实体类
		Message<T> message = new Message<>(); 
		// 远程调用
		String result = this.restTemplate.postForObject(this.remoteUrl, paramMap, String.class, restFunc);
		// 将JSON字符串转化为自定义类型的消息实体
		// new TypeToken<Message<String>>(){}.getType()
		// 上面的代码作用是让Gson解析成Message<String>类型的数据，传入具体的类型
		message = new Gson().fromJson(result, new TypeToken<Message<T>>(){}.getType());
		
		return message;
	}
	
	/**
	 * 判断远程调用结果的返回信息,只负责处理状态值和消息
	 * @param message 远程调用返回的消息体
	 * @param model 页面数据模型
	 * @param successView 正常处理的页面
	 * @param failureView 处理失败的页面
	 * @return
	 * @throws Exception 信息体为空
	 */
	protected <T> String validateResult(Message<T> message, Model model,String successView, String failureView) throws Exception {
		
		if (null == message) {
			throw new Exception("系统异常");
		}
		model.addAttribute("message", message.getMessage());
		// 根据返回值的更新数据来判断成功与否
		if ("200".equals(message.getStatus())) {
			return successView;
		} else {
			// 异常或者处理失败
			model.addAttribute("error", "1");
			return failureView;
		}
	}
	
	/**
	 * 统一异常处理：信息添加、返回信息页面
	 * @param model 页面数据模型
	 * @return
	 */
	protected String exceptionHandler(Model model) {
		model.addAttribute("error","1");
		model.addAttribute("message", "系统异常");
		return Constants.VIEW_INFO;
	}
}
