package com.mal.service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mal.service.client.BsDbClient;
import com.mal.service.dao.MallDao;
import com.mal.service.domain.Shoe;
import com.mal.service.domain.ShoeDetail;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MallService {

    @Autowired
    private MallDao mallDao;
    @Autowired
    private BsDbClient bsDbClient;

    public List<Shoe> getShoes(){
        return mallDao.getShoes();
    }

    @SneakyThrows
    public ShoeDetail getShoeDetail(Integer shoeId) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String msg = bsDbClient.get("get " + shoeId, String.valueOf(shoeId));
            System.out.println(msg);
            if (msg != null){
                System.out.println("from redis");
                return objectMapper.readValue(msg, ShoeDetail.class);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("from db");
        ShoeDetail shoeDetail = mallDao.getShoeDetail(shoeId);
        String value = objectMapper.writeValueAsString(shoeDetail);
        bsDbClient.send("set " + shoeId + " " + value);
        return shoeDetail;
    }

}
