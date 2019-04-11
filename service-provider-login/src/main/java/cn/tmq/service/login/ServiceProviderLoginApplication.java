package cn.tmq.service.login;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.tmq.service.login.mapper")
public class ServiceProviderLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceProviderLoginApplication.class, args);
	}

}
