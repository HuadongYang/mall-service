package com.mal.service.dao;

import com.mal.service.domain.BuyRecord;
import com.mal.service.domain.Mall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuyRecordDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<BuyRecord> getMallsBuyUserId(Integer userId) {
        List<BuyRecord> malls = jdbcTemplate.query("select * from buy_record where userId='"+userId+"'", new BeanPropertyRowMapper<>(BuyRecord.class));
        return malls;
    }

    public void insertBuyRecord(BuyRecord buyRecord){
        jdbcTemplate.update("insert buy_record (mallId, userId, time) values (?,?,?)", new Object[]{buyRecord.getMallId(), buyRecord.getUserId(), buyRecord.getTime()});
    }
}
