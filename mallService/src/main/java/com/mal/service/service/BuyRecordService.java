package com.mal.service.service;

import com.mal.service.dao.BuyRecordDao;
import com.mal.service.domain.BuyRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BuyRecordService {

    @Autowired
    private BuyRecordDao buyRecordDao;
    @Autowired
    private MallService mallService;

    public List<BuyRecord> getMallsBuyUserId(Integer userId) {
        return buyRecordDao.getMallsBuyUserId(userId);
    }

    public void insertBuyRecord(BuyRecord buyRecord){
        buyRecord.setTime(new Date());
        mallService.setUserMallDetail(buyRecord.getUserId(), buyRecord.getMallId());
        buyRecordDao.insertBuyRecord(buyRecord);
    }
}
