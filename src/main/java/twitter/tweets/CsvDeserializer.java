package twitter.tweets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CsvDeserializer {

    private static final String FILENAME = "sentiments.csv";

    public static Map<String, Float> getSentiments() {
        Map<String, Float> sentiments = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FileManager.getFile(FILENAME)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                sentiments.put(parts[0], Float.parseFloat(parts[1]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(sentiments);
        return sentiments;
    }

}
