package cn.tmq.gateway.fallback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;

/**
 * Edgware.RELEASE以前的版本中，zuul网关中有一个ZuulFallbackProvider接口,但是功能并不友好。
 * 后续版本中提供了FallbackProvider,并且将ZuulFallbackProvider弃用
 * 网关的回退功能
 * @author 陶敏麒
 *
 */
@Component
public class ZuulFallBack implements FallbackProvider {

	/* (non-Javadoc)
	 * @see org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider#getRoute()
	 */
	@Override
	public String getRoute() {
		// api服务id，如果需要所有调用都支持回退，则return "*"或return null
		// 例如 return service-login-provider 只回退对应service-login-provider的请求
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider#fallbackResponse(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
		// zuul网关只负责外部请求到网关 然后网关调用路由的这个过程,只要请求达到网关了,那么网关返回的响应码就为200
		// 但是,网关调用路由的过程中如果微服务宕机那么就会触发网关的回退
		// 在这个项目中,网关将请求路由到同一服务调用者处，再由调用者调用其他服务
		// 在请求达到服务调用者处后，那么网关就已经成功和微服务通信了,不会触发zuul网关的回退
		// 如果服务调用者调用其它服务的过程中其他服务宕机了，那么需要在服务调用者处主动抛出异常才能触发回退。
		// 这种情况下,就会由统一服务调用者抛出异常然后网关接受到异常从而触发回退
		// 本项目使用的是feign + hystrix
		// 如果请求用户服务失败，返回什么信息给消费者客户端
		// return null;
		try (FallBackResponse res = new FallBackResponse()){
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 内部类,用于设置响应
	 * @author 陶敏麒
	 *
	 */
	class FallBackResponse implements ClientHttpResponse {
		@Override
		public InputStream getBody() throws IOException {
			// 设置返回数据体
			JsonObject json = new JsonObject();
			json.addProperty("status", "703");
			json.addProperty("message", "网关信息:当前服务不可用");
			// 返回前端的内容，转化为数组字节流返回
			return new ByteArrayInputStream(json.toString().getBytes("UTF-8"));
		}

		@Override
		public HttpHeaders getHeaders() {
			HttpHeaders httpHeaders = new HttpHeaders();
			// 设置响应头为json
			httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
			return httpHeaders;
		}

		@Override
		public HttpStatus getStatusCode() throws IOException {
			// 网关向api服务请求是失败了，但是消费者客户端向网关发起的请求是OK的
			// 不应该把api的404,500等问题抛给客户端
			// 网关和api服务集群对于客户端来说是黑盒子
			// 请求网关成功,返回200
			return HttpStatus.OK;
		}

		@Override
		public int getRawStatusCode() throws IOException {
			// TODO Auto-generated method stub
			return HttpStatus.OK.value();
		}

		@Override
		public String getStatusText() throws IOException {
			// TODO Auto-generated method stub
			return HttpStatus.OK.getReasonPhrase();
		}

		@Override
		public void close() {
			
		}
	}

}
