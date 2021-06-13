package com.dragon.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@EnableConfigurationProperties
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.dragon" })
@MapperScan(basePackages = { "com.dragon.system.mapper", "com.dragon.system.quartz.mapper", "com.dragon.logging.mapper" })
public class DragonAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(DragonAdminApplication.class, args);
	}

}
