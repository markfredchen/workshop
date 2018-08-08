package com.cloudhelios.olympus.security.client.ip.whitelist.service;

import com.cloudhelios.olympus.security.client.ip.whitelist.domain.ClientDetail;
import com.cloudhelios.olympus.security.client.ip.whitelist.persistence.mapper.ClientDetailMapper;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

/**
 * @author markfredchen
 * @date 2018/8/3
 */
@Service
public class ClientDetailService implements ClientDetailsService {
    private final ClientDetailMapper clientDetailMapper;

    public ClientDetailService(ClientDetailMapper clientDetailMapper) {
        this.clientDetailMapper = clientDetailMapper;
    }

    @Override
    public ClientDetail loadClientByClientId(String s) throws ClientRegistrationException {
        return clientDetailMapper.selectById(s);
    }
}
