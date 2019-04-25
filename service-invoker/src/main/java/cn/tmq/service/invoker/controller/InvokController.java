package cn.tmq.service.invoker.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import cn.tmq.service.invoker.client.NoteClient;
import cn.tmq.service.invoker.client.LoginClient;

/**
 * 微服务调用者,所有的微服务的入口,外部请求通过网关后会全部交由本调用者处理,进行进一步的分发、调用
 * @author 陶敏麒
 *
 */
@RestController
public class InvokController {

	/**
	 * 使用负载均衡的RestTmplate进行远程调用
	 */
	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 使用feign框架进行远程调用(推荐)
	 */
	@Autowired
	private LoginClient loginClient;
	
	@Autowired
	private NoteClient noteClient;
	
	@Value("${server.port}")
	private String port;

	/**
	 * 远程服务分发器，模拟一个统一的路由转发器 这里注册使用resttemplate，后续使用feign框架完成 经测试,返回的json字符串不会被再次转义
	 * 
	 * @SuppressWarnings("unchecked")是由于注册使用了restTemplate的方式,此方式用于测试练习，主要使用feign的负载均衡
	 * 
	 * @param serviceId
	 * @param paramMap
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/invoke/{serviceId}", method = RequestMethod.POST)
	public Map<String, Object> registRouter(@PathVariable String serviceId, @RequestBody Map<String, String> paramMap) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			if ("regist".equals(serviceId)) {
				System.out.println("调用注册服务;参数【" + paramMap + "】");
				// Map.class 参数代表,这次请求期望返回的类型是Map类型
				// 这里如果调用出错，将不会触发回退，因为只提供了feign的回退处理，restTemplate需要另外处理
				resultMap = this.restTemplate.postForObject("http://service-provider-login/user", paramMap, Map.class);
			} else if ("login".equals(serviceId)) {
				// 登录
				System.out.println("调用登录服务;参数【" + paramMap + "】");
				resultMap = this.loginClient.login(paramMap);
			} else if ("note".equals(serviceId)) {
				// 新建笔记
				System.out.println("调用新建笔记服务;参数【" + paramMap + "】");
				resultMap = this.noteClient.note(paramMap);
			} else if ("notes".equals(serviceId)) {
				// 主页
				System.out.println("调用搜索笔记一览服务;参数【" + paramMap + "】");
				resultMap = this.noteClient.notes(paramMap);
			} else if ("view".equals(serviceId)) {
				// 查看笔记
				System.out.println("调用查看笔记服务;参数【" + paramMap + "】");
				return this.noteClient.view(paramMap.get("id"));
			} else if ("delete".equals(serviceId)) {
				// 删除笔记
				System.out.println("调用删除笔记服务;参数【" + paramMap + "】");
				return this.noteClient.delete(paramMap.get("id"));
			} else if ("update".equals(serviceId)) {
				// 修改笔记
				System.out.println("调用修改笔记服务;参数【" + paramMap + "】");
				return this.noteClient.update(paramMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 新异常会由网关捕获并触发回退
			// 如果不抛出异常或者被提前捕获，那么网关不会触发回退
			throw new Exception("调用信息:服务调用者异常");
		}
		return resultMap;
	}

	@RequestMapping("/")
	public String gobal() throws InterruptedException {
		return "这里是服务集群调用者：" + this.port;
	}
}
