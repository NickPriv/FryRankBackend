package com.fryrank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class FryRankApplication {

	public static void main(String[] args) {
		SpringApplication.run(FryRankApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins(
						// Prod URLs
						"https://pure-temple-61679-98a4d5c2d04e.herokuapp.com",
						"http://pure-temple-61679-98a4d5c2d04e.herokuapp.com",
						"https://fryrank.oxyserver.com",
						"http://fryrank.oxyserver.com",
						// Stage URLs
						"https://pure-temple-61679-beta-stage-84eefac76015.herokuapp.com",
						"http://pure-temple-61679-beta-stage-84eefac76015.herokuapp.com",
						"https://fryrank-beta-stage.oxyserver.com",
						"http://fryrank-beta-stage.oxyserver.com",
						// Local Dev Testing URL
						"http://localhost:3000"
				);
			}
		};
	}

}
