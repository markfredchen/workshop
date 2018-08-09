package com.cloudhelios.olympus.persistence.sharding.jdbc.domain;

import lombok.Data;

/**
 * @author markfredchen
 * @date 2018/8/9
 */
@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private Long tenantId;
}
