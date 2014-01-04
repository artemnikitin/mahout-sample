import org.apache.commons.io.FileUtils;
import utility.RandomGeneration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreateData {

    public static List<String> listOfUsers;

    public static void asCSV(int numberOfUsers, int numberOfItems, int itemsPerUser){
        long start = System.currentTimeMillis();
        List<String> result = new ArrayList<String>();
        List<String> users = new ArrayList<String>();
        List<String> items = new ArrayList<String>();
        String prefix = "7";
        Random rand = new Random();

        for(int i = 0; i < numberOfUsers; i++){
            StringBuilder sb = new StringBuilder();
            sb.append(prefix);
            sb.append(RandomGeneration.randomNumberNotNULL(10));
            users.add(sb.toString());
        }

        for(int i = 0; i < numberOfItems; i++){
            StringBuilder sb = new StringBuilder();
            int length = rand.nextInt(1000);
            if(length == 0) length = 1;
            sb.append(length);
            items.add(sb.toString());
        }

        result.add("#userId, itemId, score");
        for(String user: users){
            for(int i = 0; i < itemsPerUser; i++){
                StringBuilder sb = new StringBuilder();
                sb.append(user);
                sb.append(",");
                sb.append(items.get(rand.nextInt(numberOfItems)));
                sb.append(",");
                sb.append(rand.nextInt(20));
                result.add(sb.toString());
            }
        }

        try{
            FileUtils.writeLines(new File("test-data.csv"), result);
            listOfUsers = users;
            System.out.println("File saved. Time spended " + (System.currentTimeMillis() - start) + " ms");
        } catch(Exception e){
            System.out.println("Error happened when program tried to save result in file: " + e);
        }

    }

}
