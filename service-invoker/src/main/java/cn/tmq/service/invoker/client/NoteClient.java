package cn.tmq.service.invoker.client;

import java.util.Map;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import cn.tmq.service.invoker.client.fallBackClass.NoteClientFallback;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;

/**
 * feign客户端接口，用于调取服务端的接口 注解中的值指注册在eureka服务中心中的服务名,即服务提供者的spring.application.name
 * 这个接口调用的是登录功能的远程微服务 注意 FeignClient(name =
 * "service-provider-note")同一名称的微服务名称只能由一个接口声明，不能建立两个声明同样微服务名称的接口
 * 
 * @author 陶敏麒
 * @version 1.0
 */
@FeignClient(name = "service-provider-note", fallback = NoteClientFallback.class, configuration = FeignMultipartSupportConfig.class)
public interface NoteClient {

	/**
	 * 查看笔记一览 在使用feign框架的时候，默认都是post提交的，需要使用get提交时，可以使用@RequestParam来注解参数，参数少的情况下，
	 * 可以一一注明，例如@RequestParam("x") x；建议使用Map来构造，如以下方法
	 * 
	 * @param paramMap get请求方式的参数需要使用@RequestParam
	 *                 并用Map构造，如果不用@RequestParam注解,会被强制转化为post提交
	 * @return
	 */
	@RequestMapping(value = "/notes", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	Map<String, Object> notes(@RequestParam Map<String, String> paramMap);

	/**
	 * 新建笔记 需要声明传递的参数类型为json,spring会自动转化为json类型
	 * 传参时正常传参即可，被请求的controller接收时候加上@RequestBody接收参数即可自动映射到对应的bean中
	 * 最好加上consumes="application/json" 注解，声明传参类型为json
	 * 
	 * @param paramMap 这里的参数如果加上 @RequestBody注解可以达到一样的效果 例如:(@RequestBody
	 *                 Map<String, String> paramMap)
	 * @return
	 */
	@RequestMapping(value = "/note", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	Map<String, Object> note(@RequestBody Map<String, String> paramMap);

	/**
	 * 查看笔记 在使用feign框架的时候，默认都是post提交的，需要使用get提交时，可以使用@RequestParam来注解参数，参数少的情况下，
	 * 可以一一注明，例如@RequestParam("x") x；建议使用Map来构造
	 * 
	 * @param paramMap get请求方式的参数需要使用@RequestParam
	 *                 并用Map构造，如果不用@RequestParam注解,会被强制转化为post提交
	 * @return
	 */
	@RequestMapping(value = "/notes/{id}", method = RequestMethod.GET)
	Map<String, Object> view(@PathVariable String id);

	/**
	 * 删除笔记：DELETE方式提交
	 * 
	 * @param id 笔记id
	 * @return
	 */
	@RequestMapping(value = "/notes/{id}", method = RequestMethod.DELETE)
	Map<String, Object> delete(@PathVariable String id);

	/**
	 * 修改笔记: PUT方式提交
	 * 
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value = "/note", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	Map<String, Object> update(@RequestBody Map<String, String> paramMap);

	/**
	 * 上传图片
	 * 
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value = "/image", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	Map<String, Object> upload(@RequestPart("imgFile") MultipartFile file);
}

/**
 * feign支持文件上传的配置类 -> 实质上使用了新的编码器,使用了文件编码器会导致普通的封装参数解析出错,需要两者皆可的转换器来实现
 * 
 * @author 陶敏麒
 *
 */
class FeignMultipartSupportConfig {

	/**
	 * 字符集转换,这个Bean的配置只能传输文件或者字符串,如果遇到对象类型的json参数,会解析失败
	 * 
	 * @return
	 */
//	@Bean
//	public Encoder multipartFormEncoder() {
//		return new SpringFormEncoder();
//	}
	
	// ---------------------下面的转换器可以用于文件和普通参数-------------------
	
	@Autowired
	private ObjectFactory<HttpMessageConverters> messageConverters;
	          
	@Bean
	public Encoder feignFormEncoder() {
	return new SpringFormEncoder(new SpringEncoder(messageConverters));
	}
	

	@Bean
	public feign.Logger.Level multipartLoggerLevel() {
		return feign.Logger.Level.FULL;
	}
}
