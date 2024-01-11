package com.example.taskflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TaskFlowwApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskFlowwApplication.class, args);
    }

}
