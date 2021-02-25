package ru.job4j.chat;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@SpringBootApplication
public class ChatApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

    @Bean
    public SpringLiquibase liquibase(@Qualifier("dataSource") DataSource ds) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:liquibase-changeLog.xml");
        liquibase.setDataSource(ds);
        return liquibase;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ChatApplication.class);
    }
}
