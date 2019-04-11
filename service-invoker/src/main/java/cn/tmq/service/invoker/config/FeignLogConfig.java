package cn.tmq.service.invoker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;

@Configuration
public class FeignLogConfig {

	/**
	 * 日志级别枚举类 Logger.Level
	 * NONE 不输出日志
	 * BASIC 只有请求方法、URL、响应状态代码、执行时间
	 * HEADERS基本信息以及请求和响应头
	 * FULL 请求和响应 的heads、body、metadata，建议使用这个级别
	 * application.yml 也需要配置debug 日志输出级别
	 * @return
	 */
	@Bean
	Logger.Level feignLevel() {
		return Logger.Level.FULL;
	}

}
