package com.mal.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mal.service.client.BsDbClient;
import com.mal.service.dao.MallDao;
import com.mal.service.domain.Mall;
import com.mal.service.domain.MallDetail;
import com.mal.service.domain.UserMallDetail;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MallService {

    @Autowired
    private MallDao mallDao;
    @Autowired
    private BsDbClient bsDbClient;

    public List<Mall> getMallsByIds(List<Integer> ids){
        return mallDao.getMallsByIds(ids);
    }

    public List<Mall> getMalls(String type) throws InterruptedException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String msg = bsDbClient.get("get%%" + type, type);
            System.out.println("from redis msg : " + msg);
            if (msg != null){
                return objectMapper.readValue(msg, new ArrayList<Mall>().getClass());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("from db");
        List<Mall> malls = mallDao.getMalls(type);
        String value = objectMapper.writeValueAsString(malls);
        bsDbClient.send("set%%" + type + "%%" + value);
        return mallDao.getMalls(type);
    }

    @SneakyThrows
    public MallDetail getMallDetail(Integer userId, Integer mallId) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String msg = bsDbClient.get("get%%" + mallId, String.valueOf(mallId));
            System.out.println(msg);
            if (msg != null){
                System.out.println("from redis");
                MallDetail mallDetail =objectMapper.readValue(msg, MallDetail.class);
                setUserMallDetail(userId, mallDetail.getMallId());
                return mallDetail;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("from db");
        MallDetail mallDetail = mallDao.getMallDetail(mallId);
        String value = objectMapper.writeValueAsString(mallDetail);
        bsDbClient.send("set%%" + mallId + "%%" + value);
        setUserMallDetail(userId, mallDetail.getMallId());
        return mallDetail;
    }

    public void setUserMallDetail(Integer userId, Integer mallId){
        UserMallDetail userMallDetail = mallDao.getUserMallDetail(userId, mallId);
        if (userMallDetail == null) {
            mallDao.insertUserMallDetail(userId, mallId, 1);
            return;
        }
        mallDao.updateUserMallDetail(userMallDetail.getId(), userMallDetail.getPreferenceValue() + 1);
    }

    public List<UserMallDetail> getUserMallDetailByUserIds(Integer userId, Long[] otherIds){
        return mallDao.getUserMallDetailByUserIds(userId, otherIds);
    }

}
