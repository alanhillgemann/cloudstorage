package com.udacity.jwdnd.course1.cloudstorage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
public class CloudStorageApplication {

	public static final Logger LOGGER=LoggerFactory.getLogger(CloudStorageApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CloudStorageApplication.class, args);
	}

	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(DataSize.ofBytes(1073741824L));
		factory.setMaxRequestSize(DataSize.ofBytes(1073741824L));
		return factory.createMultipartConfig();
	}
}
