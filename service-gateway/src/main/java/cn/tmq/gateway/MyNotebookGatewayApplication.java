package cn.tmq.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


/**
 * @EnableZuulProxy 表示这是一个网关项目
 * 需要在yml配置文件中将其配置到服务集群中
 * @author 陶敏麒
 *
 */
@EnableZuulProxy
@SpringBootApplication
public class MyNotebookGatewayApplication {

	public static void main(String[] args) {
		/*HystrixCommandProperties.Setter()
		   .withExecutionTimeoutInMilliseconds(6000);*/
		SpringApplication.run(MyNotebookGatewayApplication.class, args);
	}

}
