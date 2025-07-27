package com.example.reputeo.taskReputeo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class TaskReputeoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskReputeoApplication.class, args);
	}

}
