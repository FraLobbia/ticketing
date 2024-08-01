package com.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.backend.model.User;
@SpringBootApplication
@EnableJpaRepositories("com.backend.repository")
@EntityScan("com.backend.model")
@ComponentScan(basePackages = { "com.backend.*" })
public class BackendApplication {

    public static void main(String[] args) {
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			configuration.addAnnotatedClass(User.class);
			try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
				Session session = sessionFactory.openSession();
				session.beginTransaction();
				User user = new User();
				user.setName("John");
				user.setSurname("Doe");
				
				
				session.getTransaction().commit();
			}
			catch (Exception e) {
				e.printStackTrace();
			}




        SpringApplication.run(BackendApplication.class, args);
    }
}
