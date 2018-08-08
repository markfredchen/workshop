package com.cloudhelios.olympus.security.client.ip.whitelist.config;

import com.cloudhelios.olympus.security.client.ip.whitelist.service.ClientDetailService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @author markfredchen
 * @date 2018/8/3
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizeServerConfiguration extends AuthorizationServerConfigurerAdapter {
    private final ClientDetailService clientDetailService;

    public AuthorizeServerConfiguration(ClientDetailService clientDetailService) {
        this.clientDetailService = clientDetailService;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailService);
    }
}
