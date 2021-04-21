package com.mal.service.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mal.service.domain.BuyRecord;
import com.mal.service.domain.Mall;
import com.mal.service.domain.UserMallDetail;
import com.mal.service.service.BasedUserRecommendationService;
import com.mal.service.service.BuyRecordService;
import com.mal.service.service.MallService;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mall")
public class MallApi {
    @Autowired
    private MallService mallService;
    @Autowired
    private BasedUserRecommendationService basedUserRecommendationService;
    @Autowired
    private BuyRecordService buyRecordService;

    @GetMapping("/type/{type}")
    public List getMallsByType(@PathVariable String type, @RequestParam Integer userId) throws Exception {
        return mallService.getMalls(type);
    }

    @GetMapping("/{type}/{id}")
    public Object getMallDetail(@PathVariable String type, @PathVariable Integer id, @RequestParam Integer userId) throws Exception {
        if (userId == null) {
            throw new Exception("logout");
        }
        return mallService.getMallDetail(userId, id);
    }

    @GetMapping("/test")
    public void test() throws TasteException {
        basedUserRecommendationService.test();
    }
    @GetMapping("/recommend")
    public List<Mall> getRecommendMalls(@RequestParam Integer userId) throws TasteException {
        List<Integer> mallIds = basedUserRecommendationService.getRecommendMallId(userId);
        if (CollectionUtils.isEmpty(mallIds)) {
            return null;
        }
        return mallService.getMallsByIds(mallIds);
    }

    @GetMapping("/recommend/user")
    public List<Mall> getRecommendMallIdByUserNei(@RequestParam Integer userId) throws TasteException {
        long[] userIds = basedUserRecommendationService.getRecommendMallIdByUserNei(userId);
        if (userIds.length == 0) {
            return null;
        }
        List<UserMallDetail> userMallDetails = mallService.getUserMallDetailByUserIds(userId, Arrays.stream(userIds).boxed().toArray(Long[]::new));
        if (CollectionUtils.isEmpty(userMallDetails)) {
            return null;
        }
        return mallService.getMallsByIds(userMallDetails.stream().map(UserMallDetail::getMallId).collect(Collectors.toList()));
    }

    @GetMapping("/buyRecord")
    public List<BuyRecord> getMallsBuyUserId(@RequestParam Integer userId) {
        List<BuyRecord> records = buyRecordService.getMallsBuyUserId(userId);
        if (CollectionUtils.isEmpty(records)) {
            return null;
        }
        List<Integer> mallIds = records.stream().map(BuyRecord::getMallId).collect(Collectors.toList());
        List<Mall> malls = mallService.getMallsByIds(mallIds);
        Map<Integer, Mall> mallId2ItemMap = malls.stream().collect(Collectors.toMap(Mall::getId, x -> x));
        records.forEach(buyRecord -> {
            Mall mall = mallId2ItemMap.get(buyRecord.getMallId());
            if (mall == null) {
                return;
            }
            buyRecord.setBrand(mall.getBrand());
            buyRecord.setDescription(mall.getDescription());
            buyRecord.setName(mall.getName());
            buyRecord.setUrl(mall.getUrl());
            buyRecord.setPrice(mall.getPrice());
        });

        return records;
    }

    @PostMapping("/buyRecord/insert")
    public void insertBuyRecord(@RequestBody BuyRecord buyRecord) {
        buyRecordService.insertBuyRecord(buyRecord);
    }
}
