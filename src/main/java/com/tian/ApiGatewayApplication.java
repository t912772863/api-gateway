package com.tian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * @EnableZuulProxy 注解开启zuul功能.
 *
 * 对外统一的网关,
 *
 * 转发到eureka-client服务的请求规则为：/eureka-client/**
 * 转发到eureka-consumer服务的请求规则为：/eureka-consumer/**
 *
 */
@EnableZuulProxy
@SpringBootApplication
public class ApiGatewayApplication {

	/**
	 * 定义的过滤器, 默认不会生效, 这里初始化就可以了.
	 * @return
	 */
	@Bean
	public AccessFilter accessFilter(){
		return new AccessFilter();
	}

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
}
