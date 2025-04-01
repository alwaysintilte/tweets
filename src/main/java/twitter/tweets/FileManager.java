package twitter.tweets;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FileManager {
    public static Map<String,String> fileNames=Map.of(
            "cali", "cali_tweets2014.txt",
            "family", "family_tweets2014.txt",
            "football", "football_tweets2014.txt",
            "highschool", "high_school_tweets2014.txt",
            "movie", "movie_tweets2014.txt",
            "shopping", "shopping_tweets2014.txt",
            "snow", "snow_tweets2014.txt",
            "texas", "texas_tweets2014.txt",
            "weekend", "weekend_tweets2014.txt"
    );
    public static File getFile(String filename) {
        String data = Objects.requireNonNull(TweetParser.class.getClassLoader().getResource("Data").getPath());
        File[] files = new File(data).listFiles(((dir, name) -> name.contains(filename)));
        if (files != null && files.length > 0) {
            return files[0];
        }
        return null;
    }
    public static String getFileName(String fileNameSite) {
        return fileNames.get(fileNameSite);
    }
}
