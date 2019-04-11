package cn.tmq.service.notebook;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.tmq.service.notebook.mapper")
public class ServiceProviderNotebookApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceProviderNotebookApplication.class, args);
	}

}
