package com.tut.flowable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.tut.flowable.controllers")
public class TutorialApplication implements ApplicationRunner {

	private static Logger log = LoggerFactory.getLogger(TutorialApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TutorialApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		log.info("Tutorial application is started ....");

	}
}
