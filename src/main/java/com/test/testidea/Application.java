package com.test.testidea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * Application Main Entry
 *
 * @author ifzm
 * @version 0.1
 * @date 2019/3/4 20:55
 */
@SpringBootApplication
@EntityScan(basePackageClasses = { Application.class, Jsr310JpaConverters.class })
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
