package com.learn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan("com.learn.mapper")
@EnableFeignClients
// 禁用spring默认的数据源配置
// 只使用自定义配置 DSPAutoConfiguration
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

}
