package com.mal.service.dao;

import com.mal.service.domain.Mall;
import com.mal.service.domain.MallDetail;
import com.mal.service.domain.UserMallDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class MallDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Mall> getMalls(String type) {
        List<Mall> malls = jdbcTemplate.query("select * from mall where type='"+type+"'", new BeanPropertyRowMapper<>(Mall.class));
        return malls;
    }

    public List<Mall> getMallsByIds(List<Integer> mallIds) {
        List<Mall> malls = jdbcTemplate.query("select * from mall where id in ("+ StringUtils.collectionToCommaDelimitedString(mallIds) +")", new BeanPropertyRowMapper<>(Mall.class));
        return malls;
    }

    public MallDetail getMallDetail(Integer mallId) {
        List<MallDetail> shoes = jdbcTemplate.query("select * from mall_detail where mallId = " + mallId, new BeanPropertyRowMapper<>(MallDetail.class));
        if (CollectionUtils.isEmpty(shoes)) {
            return null;
        }
        return shoes.get(0);
    }

    public UserMallDetail getUserMallDetail(Integer userId, Integer mallDetailId) {
        List<UserMallDetail> userMallDetails = jdbcTemplate.query("select * from user_mall_detail where userId="+userId+" and mallId="+mallDetailId, new BeanPropertyRowMapper<>(UserMallDetail.class));
        if (userMallDetails.size() > 0) {
            return userMallDetails.get(0);
        }
        return null;
    }

    public List<UserMallDetail> getUserMallDetailByUserIds(Integer userId, Long[] otherIds) {
        List<UserMallDetail> userMallDetails = jdbcTemplate.query(
                "select * from user_mall_detail where userId in ("+StringUtils.arrayToCommaDelimitedString(otherIds)+") and mallId not in (select mallId from user_mall_detail de where de.userId="+userId+")",
                new BeanPropertyRowMapper<>(UserMallDetail.class));
        return userMallDetails;
    }

    public void insertUserMallDetail(Integer userId, Integer mallId, Integer preferenceValue){
        jdbcTemplate.execute("insert user_mall_detail (userId, mallId, preferenceValue) values ("+userId+","+mallId+","+preferenceValue+")");
    }

    public void updateUserMallDetail(Integer id,Integer preferenceValue){
        jdbcTemplate.execute("update user_mall_detail set preferenceValue="+preferenceValue+" where id="+id);
    }
}
