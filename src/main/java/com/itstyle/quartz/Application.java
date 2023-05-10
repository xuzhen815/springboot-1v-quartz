package com.itstyle.quartz;

import org.apache.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @author xuzhen
 * @version 1.0
 * @date 2023/5/5 16:05
 */
@MapperScan("com.itstyle.quartz.**.mapper")
@SpringBootApplication
public class Application {
	private static final Logger logger = Logger.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		logger.info("项目启动 ");
	}
}
