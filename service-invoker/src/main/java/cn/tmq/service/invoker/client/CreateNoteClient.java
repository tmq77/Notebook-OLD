package cn.tmq.service.invoker.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.tmq.service.invoker.client.fallBackClass.CreateNoteClientFallback;

/**
 * feign客户端接口，用于调取服务端的接口
 *  注解中的值指注册在eureka服务中心中的服务名,即服务提供者的spring.application.name
 *  这个接口调用的是登录功能的远程微服务
 * @author 陶敏麒
 * @version 1.0
 */
@FeignClient(name = "service-provider-note",fallback = CreateNoteClientFallback.class)
public interface CreateNoteClient {
	
	/**
	 * 在使用feign框架的时候，默认都是post提交的，需要使用get提交时，可以使用@RequestParam来注解参数，参数少的情况下，
	 * 可以一一注明，例如@RequestParam("x") x；建议使用Map来构造，如以下方法
	 * @param paramMap get请求方式的参数需要使用@RequestParam 并用Map构造，如果不用@RequestParam注解,会被强制转化为post提交
	 * @return
	 */
	@RequestMapping(value = "/notes", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	Map<String, Object> notes(@RequestParam Map<String, String> paramMap);
	
	/**
	 * 需要声明传递的参数类型为json,spring会自动转化为json类型
	 * 传参时正常传参即可，被请求的controller接收时候加上@RequestBody接收参数即可自动映射到对应的bean中
	 * 最好加上consumes="application/json" 注解，声明传参类型为json 
	 * @param paramMap  这里的参数如果加上 @RequestBody注解可以达到一样的效果 例如:(@RequestBody Map<String, String> paramMap)
	 * @return
	 */
	@RequestMapping(value="/note", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	Map<String, Object> note(Map<String, String> paramMap);
}
