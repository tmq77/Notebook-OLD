package cn.tmq.service.invoker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 第一个注解为springboot 自动配置注解,注解内包含三个注解，表明这是一个spring boot程序
 * 第二个注解为开启eureka的服务发现，同时也表明自己是一个eureka客户端
 * 第三个注解是feign开关，表明这是一个feign客户端程序，封装了负载均衡的功能
 * 第四個注解是hystrix的注解,表示开启断路器
 * @author 陶敏麒
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class ServiceInvokerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceInvokerApplication.class, args);
	}

	/**
	 * RestTemplate注入有问题 新版的需要这样注入：
	 * @LoadBalanced 为了实现负载均衡
	 * 1.4以后需要手动配置一个restTemplate，也可以单独新建一个@configuration注解的类，类中包含以下代码即可，因为启动类自带了配置注解
	 * 所以写在启动类里也行
	 * 然后在后续需要使用的地方autowired即可
	 * @param builder
	 * @return
	 */
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

}
