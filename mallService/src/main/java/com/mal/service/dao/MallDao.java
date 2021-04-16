package com.mal.service.dao;

import com.mal.service.domain.Shoe;
import com.mal.service.domain.ShoeDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Component
public class MallDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Shoe> getShoes() {
        List<Shoe> shoes = jdbcTemplate.query("select * from shoes", new BeanPropertyRowMapper<>(Shoe.class));
        return shoes;
    }

    public ShoeDetail getShoeDetail(Integer shoeId) {
        List<ShoeDetail> shoes = jdbcTemplate.query("select * from shoe_detail where shoeId = " + shoeId, new BeanPropertyRowMapper<>(ShoeDetail.class));
        if (CollectionUtils.isEmpty(shoes)) {
            return null;
        }
        return shoes.get(0);
    }
}
