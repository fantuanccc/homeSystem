package com.hua.furnitureManagement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Slf4j
@EnableScheduling //开启任务调度
public class FurnitureManagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(FurnitureManagementApplication.class, args);
		log.info("server started");
	}

}
