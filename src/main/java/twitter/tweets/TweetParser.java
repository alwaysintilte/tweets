package twitter.tweets;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TweetParser {
    public static List<Tweet> parseTweets() {
        File[] files = null;
        try {
            String data = Objects.requireNonNull(TweetParser.class.getClassLoader().getResource("Data").getPath());
             files = new File(data).listFiles(((dir, name) -> name.endsWith(".txt")));
        } catch (NullPointerException ignored) {
        }
        System.out.println(Arrays.toString(files));
        return List.of();
    }
}
