package com.cloudhelios.olympus.persistence.sharding.jdbc.domain;

import lombok.Data;

/**
 * @author markfredchen
 * @date 2018/8/8
 */
@Data
public class Tenant {
    private Long id;
    private String tenantCode;

}
