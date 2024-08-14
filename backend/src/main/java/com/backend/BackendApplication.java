package com.backend;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.backend.repository")
@ComponentScan(basePackages = { "com.backend.*" })
public class BackendApplication {

	private static final Logger logger = LoggerFactory.getLogger(BackendApplication.class);

	public static void main(String[] args) {

		try {
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			configuration.buildSessionFactory();
		} catch (HibernateException e) {
			logger.error("HibernateException occurred", e);
		} catch (Exception e) {
			logger.error("Exception occurred", e);
		}

		SpringApplication.run(BackendApplication.class, args);
	}
}
