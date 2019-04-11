package cn.tmq.service.notebook;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.tmq.service.blog.mapper")
public class ServiceProviderBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceProviderBlogApplication.class, args);
	}

}
