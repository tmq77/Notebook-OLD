package cn.tmq.myNotebook;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@MapperScan("cn.tmq.myNotebook.dao.mapper")
public class MyNoteBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyNoteBookApplication.class, args);
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
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

}
