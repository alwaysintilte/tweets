package twitter.tweets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TweetParser {

    public static List<Tweet> parseFile(String filename) {
        List<Tweet> tweets = new ArrayList<>();
        File file = FileManager.getFile(filename);
        if (file == null) {
            return List.of();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");

                String coordinates = parts[0].replaceAll("[\\[\\] ]", "");
                double latitude = Double.parseDouble(coordinates.split(",")[1]);
                double longitude = Double.parseDouble(coordinates.split(",")[0]);

                tweets.add(new Tweet(latitude, longitude, parts[3], null));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tweets;
    }
}
