package com.mal.service.service;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasedUserRecommendationService {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private DataModel dataModel;

    public List<Integer> getRecommendMallId(Integer userId) throws TasteException {
        UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(dataModel);
        //定义用户的2-最近邻
        UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(2, userSimilarity, dataModel);
        //定义推荐引擎
        Recommender recommender = new GenericUserBasedRecommender(dataModel,userNeighborhood, userSimilarity);
        List<RecommendedItem> recommendedItems = recommender.recommend(userId, 2);
        if (CollectionUtils.isEmpty(recommendedItems)) {
            return new ArrayList<>();
        }
        return recommendedItems.stream().map(x->Long.valueOf(x.getItemID()).intValue()).collect(Collectors.toList());
    }

    public long[] getRecommendMallIdByUserNei(Integer userId) throws TasteException {
        UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(dataModel);
        //定义用户的2-最近邻
        UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(2, userSimilarity, dataModel);
        //定义推荐引擎
        long[] userN = userNeighborhood.getUserNeighborhood(userId);
        return userN;
    }

    public void test() throws TasteException {
        //DataModel dataModel = new MySQLJDBCDataModel(dataSource, "user_mall_detail", "userId", "mallId", "preferenceValue", "timestamp");
        UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(dataModel);

        //定义用户的2-最近邻
        UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(2, userSimilarity, dataModel);
        //定义推荐引擎
        Recommender recommender = new GenericUserBasedRecommender(dataModel,userNeighborhood, userSimilarity);
        //从数据模型中获取所有用户ID迭代器
        LongPrimitiveIterator usersIterator = dataModel.getUserIDs();
        //通过迭代器遍历所有用户ID
        while (usersIterator.hasNext()) {
            System.out.println("================================================");
            //用户ID
            long userID = usersIterator.nextLong();
            //用户ID
            LongPrimitiveIterator otherusersIterator = dataModel.getUserIDs();
            //遍历用户ID，计算任何两个用户的相似度
            while (otherusersIterator.hasNext()) {
                Long otherUserID = otherusersIterator.nextLong();
                System.out.println("用户 " + userID + " 与用户 " + otherUserID + " 的相似度为："
                        + userSimilarity.userSimilarity(userID, otherUserID));
            }
            //userID的N-最近邻
            long[] userN = userNeighborhood.getUserNeighborhood(userID);
            //用户userID的推荐物品，最多推荐两个
            List<RecommendedItem> recommendedItems = recommender.recommend(userID, 2);
            System.out.println("用户 "+userID + " 的2-最近邻是 "+ Arrays.toString(userN));
            if (recommendedItems.size() > 0) {
                for (RecommendedItem item : recommendedItems) {
                    System.out.println("推荐的物品"+ item.getItemID()+"预测评分是 "+ item.getValue());
                }
            } else {
                System.out.println("无任何物品推荐");
            }
        }
    }
}
