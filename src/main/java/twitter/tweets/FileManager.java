package twitter.tweets;

import java.io.File;
import java.util.Objects;

public class FileManager {
    public static File getFile(String filename) {
        String data = Objects.requireNonNull(TweetParser.class.getClassLoader().getResource("Data").getPath());
        File[] files = new File(data).listFiles(((dir, name) -> name.contains(filename)));
        if (files != null && files.length > 0) {
            return files[0];
        }
        return null;
    }
}
