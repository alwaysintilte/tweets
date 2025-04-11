package twitter.tweets;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static twitter.tweets.FileManager.getFileName;
import static twitter.tweets.TweetAnalyzer.calculateAverageSentiments;
import static twitter.tweets.TweetAnalyzer.groupTweetsByState;

@RestController
public class ControllerWeb {
    @GetMapping("/getsentimentsonload")
    public Map<String, Double> getSentimentsOnLoad(String mapNameText) throws IOException {
        List<Tweet> tweets = TweetParser.parseFile(getFileName(mapNameText));
        Map<String, State> states = JsonMethods.JsonDeserializer("src/main/resources/Data/states.json");
        TweetAnalyzer.setSentimentsToTweets(tweets, CsvDeserializer.getSentiments());
        Map<String, List<Tweet>> groupedTweets = groupTweetsByState(tweets, states);
        return calculateAverageSentiments(groupedTweets);
    }
}
