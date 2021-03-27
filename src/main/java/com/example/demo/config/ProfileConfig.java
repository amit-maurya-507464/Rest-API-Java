package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ProfileConfig {

	private static Logger LOGGER = LoggerFactory.getLogger(ProfileConfig.class);
	
	 	@Profile(value = "dev")
	    @Bean
	    public void devConfig() {
	 		LOGGER.info("Successfully loaded the development environment.");
	    }
}
