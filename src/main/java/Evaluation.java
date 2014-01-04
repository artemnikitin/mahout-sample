import org.apache.commons.cli2.OptionException;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;

public class Evaluation {

    public static void main(String... args) throws IOException, TasteException, OptionException {

        RecommenderBuilder builder = new RecommenderBuilder() {
            public Recommender buildRecommender(DataModel model) throws TasteException {
                //you can put here whatever existing or customized recommendation algorithm
                UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(model);
                UserNeighborhood neighborhood = new NearestNUserNeighborhood(5, userSimilarity, model);
                Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, userSimilarity);
                return new CachingRecommender(recommender);
            }
        };

        RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
        DataModel model = new FileDataModel(new File("test-data.csv"));
        double score = evaluator.evaluate(builder, null, model, 0.9, 1);

        System.out.println("Evaluation score is "+score);
    }

}
