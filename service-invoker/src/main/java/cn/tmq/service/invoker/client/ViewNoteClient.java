package cn.tmq.service.invoker.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.tmq.service.invoker.client.fallBackClass.ViewNoteClientFallback;

@FeignClient(name = "service-provider-note", fallback = ViewNoteClientFallback.class)
public interface ViewNoteClient {

	/**
	 * 在使用feign框架的时候，默认都是post提交的，需要使用get提交时，可以使用@RequestParam来注解参数，参数少的情况下，
	 * 可以一一注明，例如@RequestParam("x") x；建议使用Map来构造，如以下方法
	 * @param paramMap get请求方式的参数需要使用@RequestParam 并用Map构造，如果不用@RequestParam注解,会被强制转化为post提交
	 * @return
	 */
	@RequestMapping(value = "/note", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	Map<String, Object> view(@RequestParam Map<String, String> paramMap);
}
