package com.smetnerassosiates;

import com.smetnerassosiates.repositories.ThemeRepository;
import com.smetnerassosiates.util.DBfiller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		DBfiller dBfiller = context.getBean(DBfiller.class);
		dBfiller.fill();
	}

}

