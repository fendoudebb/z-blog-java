package com.msj.blog;

import com.msj.blog.event.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@EnableAsync //配合@Async注解
@EnableScheduling //定时任务
public class MsjBlogApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(MsjBlogApplication.class);
		springApplication.addListeners(new ApplicationStartingEventListener());
		springApplication.addListeners(new ApplicationEnvironmentPreparedEventListener());
		springApplication.addListeners(new ApplicationPreparedEventListener());
		springApplication.addListeners(new ApplicationStartedEventListener());
		springApplication.addListeners(new ApplicationReadyEventListener());
		springApplication.addListeners(new ApplicationFailedEventListener());
		springApplication.run(args);
	}
}
