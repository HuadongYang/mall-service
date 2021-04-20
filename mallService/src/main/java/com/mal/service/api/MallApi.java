package com.mal.service.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mal.service.domain.Mall;
import com.mal.service.domain.UserMallDetail;
import com.mal.service.service.BasedUserRecommendationService;
import com.mal.service.service.MallService;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mall")
public class MallApi {
    @Autowired
    private MallService mallService;
    @Autowired
    private BasedUserRecommendationService basedUserRecommendationService;

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
}
