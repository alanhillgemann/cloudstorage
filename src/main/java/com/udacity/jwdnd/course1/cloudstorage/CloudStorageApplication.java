package com.udacity.jwdnd.course1.cloudstorage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CloudStorageApplication {

	public static final Logger LOGGER=LoggerFactory.getLogger(CloudStorageApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CloudStorageApplication.class, args);
	}

}
