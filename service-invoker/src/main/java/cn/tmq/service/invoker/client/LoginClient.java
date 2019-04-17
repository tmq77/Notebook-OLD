package cn.tmq.service.invoker.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.tmq.service.invoker.client.fallBackClass.LoginClientFallback;

/**
 * feign客户端接口，用于调取服务端的接口
   *  注解中的值指注册在eureka服务中心中的服务名,即服务提供者的spring.application.name
   *  这个接口调用的是登录功能的远程微服务
   *  
 * @author 陶敏麒
 *
 */
@FeignClient(name = "service-provider-login",fallback = LoginClientFallback.class)
public interface LoginClient {
	
	/**
	 * 需要声明传递的参数类型为json,spring会自动转化为json类型
	 * 传参时正常传参即可，被请求的controller接收时候加上@RequestBody接收参数即可自动映射到对应的bean中
	 * 最好加上consumes="application/json" 注解，声明传参类型为json 
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	Map<String, Object> login(Map<String, String> paramMap);
	
	/**
	 * 声明传递的参数类型为json,spring会自动转化为json类型
	 * 传参时正常传参即可，被请求的controller接收时候加上@RequestBody接收参数即可自动映射到对应的bean中
	 * 最好加上consumes="application/json" 注解，声明传参类型为json 
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value="/user", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	Map<String, Object> regist(Map<String, String> paramMap);
}
