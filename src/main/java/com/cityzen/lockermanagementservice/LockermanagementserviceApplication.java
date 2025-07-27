package com.cityzen.lockermanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.cityzen.lockermanagementservice.clients")
@SpringBootApplication
public class LockermanagementserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LockermanagementserviceApplication.class, args);
	}

}
