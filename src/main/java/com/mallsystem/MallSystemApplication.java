package com.mallsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created with Eclipse
 * @author heroC
 * @since JDK1.8
 * @version 1.0
 * Description: 该类是整个项目的启动入口
 */
@SpringBootApplication
public class MallSystemApplication {
//	应用启动入口(启动页)
	public static void main(String[] args) {
		SpringApplication.run(MallSystemApplication.class, args);
	}

}
