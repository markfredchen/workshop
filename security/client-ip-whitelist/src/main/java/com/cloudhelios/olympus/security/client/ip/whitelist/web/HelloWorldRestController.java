package com.cloudhelios.olympus.security.client.ip.whitelist.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author markfredchen
 * @date 2018/8/3
 */
@RestController
public class HelloWorldRestController {
    @RequestMapping("/api/hello")
    public String greeting() {
        return "Hello";
    }
}
