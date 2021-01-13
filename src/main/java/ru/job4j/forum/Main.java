package ru.job4j.forum;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import ru.job4j.forum.config.SecurityConfig;

import javax.sql.DataSource;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 03.12.2020.
 */
@SpringBootApplication
public class Main extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Main.class, SecurityConfig.class);
    }

    @Bean
    public SpringLiquibase liquibase(@Qualifier("dataSource") DataSource ds) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:master.xml");
        liquibase.setDataSource(ds);
        return liquibase;
    }
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
