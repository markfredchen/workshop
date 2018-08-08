package com.cloudhelios.olympus.bean.modification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author markfredchen
 * @date 2018/8/8
 */
@SpringBootApplication
public class BeanModificationApplication {
    public static void main(String[] args) {
        SpringApplication.run(BeanModificationApplication.class, args);
    }
}

@Service
class BeanService {
    private String beanMessage = "initBeanMessage";

    public String getBeanMessage() {
        return beanMessage;
    }

    public void setBeanMessage(String beanMessage) {
        this.beanMessage = beanMessage;
    }
}

@Component
class AppRunner implements CommandLineRunner {

    @Autowired
    private ApplicationContext context;

    @Override
    public void run(String... args) throws Exception {
        BeanService beanService = context.getBean(BeanService.class);
        System.out.println(beanService.getBeanMessage());
        beanService.setBeanMessage("Updated Message");
        System.out.println(beanService.getBeanMessage());
    }
}