package com.mal.service.service;

import com.mal.service.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User login(String account, String password) {

        List<User> users = jdbcTemplate.query("select * from user where account = '" + account +"'", new BeanPropertyRowMapper<>(User.class));
        if (users.size() > 0) {
            return users.get(0);
        }
        return null;
    }


}
