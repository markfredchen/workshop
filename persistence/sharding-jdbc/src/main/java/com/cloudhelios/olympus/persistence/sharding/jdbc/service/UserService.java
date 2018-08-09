package com.cloudhelios.olympus.persistence.sharding.jdbc.service;

import com.cloudhelios.olympus.persistence.sharding.jdbc.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author markfredchen
 * @date 2018/8/9
 */
@Service
public class UserService {
    private final JdbcTemplate template;

    public UserService(JdbcTemplate template) {
        this.template = template;
    }

    public void createUser(User user) {
        template.execute(String.format("INSERT INTO op_user(username, password_hash, tenant_id) VALUES('%s', '%s', %d)", user.getUsername(), user.getPassword(), user.getTenantId()));
    }

    public List<User> getUsers() {
        return template.query("SELECT * FROM op_user WHERE tenant_id > 100001", (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password_hash"));
            user.setTenantId(rs.getLong("tenant_id"));
            return user;
        });
    }
}
