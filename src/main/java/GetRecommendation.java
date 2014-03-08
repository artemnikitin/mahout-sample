import java.io.*;
import java.util.*;

import org.apache.mahout.cf.taste.impl.model.file.*;
import org.apache.mahout.cf.taste.impl.neighborhood.*;
import org.apache.mahout.cf.taste.impl.recommender.*;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.*;
import org.apache.mahout.cf.taste.neighborhood.*;
import org.apache.mahout.cf.taste.recommender.*;
import org.apache.mahout.cf.taste.similarity.*;

public class GetRecommendation {

    public static void main(String[] args) throws Exception {
        CreateData crData = new CreateData();
        crData.asCSV(10000, 200, 10);
        List<String> users = crData.getListOfUsers();
        File data = new File("test-data.csv");
        DataModel model = new FileDataModel(data);

        UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(model);
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(5, userSimilarity, model);
        Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, userSimilarity);
        Recommender cachingRecommender = new CachingRecommender(recommender);

        Random rand = new Random();
        List<RecommendedItem> recommendations = cachingRecommender.recommend(
                Long.parseLong(users.get(rand.nextInt(users.size()))), 5);

        if (recommendations.size() == 0) {
            System.out.println("There is no recommendation for selected user.");
        }
        else {
            for (RecommendedItem recommendation : recommendations) {
                System.out.println(recommendation);
            }
        }

    }

}
