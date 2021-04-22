package com.mal.service.service;

import com.alibaba.fastjson.JSONObject;
import com.bs.bean.beans.DataType;
import com.bs.bean.beans.KVObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mal.service.client.BsDbClient;
import com.mal.service.dao.MallDao;
import com.mal.service.domain.Mall;
import com.mal.service.domain.MallDetail;
import com.mal.service.domain.UserMallDetail;
import io.netty.util.internal.ObjectUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class MallService {

    @Autowired
    private MallDao mallDao;
    @Autowired
    private BsDbClient bsDbClient;

    public List<Mall> getMallsByIds(List<Integer> ids) {
        return mallDao.getMallsByIds(ids);
    }

    public List<Mall> getMalls(String type) throws InterruptedException, JsonProcessingException {
        System.out.println("from db");
        return mallDao.getMalls(type);
    }

    @SneakyThrows
    public MallDetail getMallDetail(Integer userId, Integer mallId) {
        ObjectMapper objectMapper = new ObjectMapper();
        MallDetail mallDetail = bsDbClient.get(String.valueOf(mallId), MallDetail.class);
        if (mallDetail != null) {
            System.out.println("from redis");
            System.out.println(JSONObject.toJSONString(mallDetail));
            setUserMallDetail(userId, mallDetail.getMallId());
            return mallDetail;
        }

        System.out.println("from db");
        mallDetail = mallDao.getMallDetail(mallId);
        String value = objectMapper.writeValueAsString(mallDetail);
        bsDbClient.set(String.valueOf(mallId), new KVObject(DataType.STRING_TYPE, value));
        setUserMallDetail(userId, mallDetail.getMallId());
        return mallDetail;
    }

    public void setUserMallDetail(Integer userId, Integer mallId) {
        UserMallDetail userMallDetail = mallDao.getUserMallDetail(userId, mallId);
        if (userMallDetail == null) {
            mallDao.insertUserMallDetail(userId, mallId, 1);
            return;
        }
        mallDao.updateUserMallDetail(userMallDetail.getId(), userMallDetail.getPreferenceValue() + 1);
    }

    public List<UserMallDetail> getUserMallDetailByUserIds(Integer userId, Long[] otherIds) {
        return mallDao.getUserMallDetailByUserIds(userId, otherIds);
    }

}
