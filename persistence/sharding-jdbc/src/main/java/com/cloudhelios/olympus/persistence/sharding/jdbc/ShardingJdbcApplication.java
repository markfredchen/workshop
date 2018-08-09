package com.cloudhelios.olympus.persistence.sharding.jdbc;

import com.cloudhelios.olympus.persistence.sharding.jdbc.domain.User;
import com.cloudhelios.olympus.persistence.sharding.jdbc.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

/**
 * @author markfredchen
 * @date 2018/8/8
 */
@SpringBootApplication
public class ShardingJdbcApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShardingJdbcApplication.class, args);
    }
}

@Component
class AppRunner implements CommandLineRunner {
    @Autowired
    UserService userService;
    @Override
    public void run(String... args) {

        User user = new User();
        user.setUsername(RandomStringUtils.randomAlphabetic(10));
        user.setPassword(RandomStringUtils.randomAlphabetic(10));
        user.setTenantId(100002L);
        userService.createUser(user);

        System.out.println(userService.getUsers());
    }
}