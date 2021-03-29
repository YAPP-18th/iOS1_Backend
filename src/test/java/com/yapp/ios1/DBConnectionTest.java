package com.yapp.ios1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest(properties = "spring.config.location=classpath:application.yml")
public class DBConnectionTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.datasource.driver-class-name}")
    private String DRIVER;

    @Value("${spring.datasource.url}")
    private String URL;

    @Value("${spring.datasource.username}")
    private String USER;

    @Value("${spring.datasource.password}")
    private String PASSWORD;

    @Test
    public void testConnection() throws ClassNotFoundException {
        Class.forName(DRIVER);
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            logger.info("Connection : " + connection);
        } catch (Exception e) {
            logger.info("Exception : " + e.getMessage());
        }
    }

}
