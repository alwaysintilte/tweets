package twitter.tweets;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static twitter.tweets.TweetAnalyzer.calculateAverageSentiments;
import static twitter.tweets.TweetAnalyzer.groupTweetsByState;

@RestController
public class ControllerWeb {
    @GetMapping("/getsentimentsonload")
    public Map<String, Double> getSentimentsOnLoad() throws IOException {
        Map<String, State> states = JsonMethods.JsonDeserializer("src/main/resources/static/states.json");
        List<Tweet> tweets = Arrays.asList(
                new Tweet(-118.2437, 34.0522, "Love the weather in LA!", 0.9), // California
                new Tweet(-74.0060, 40.7128, "New York is amazing!", -0.4), // New York
                new Tweet(-95.3698, 29.7604, "Texas BBQ is the best!", 0.1), // Texas
                new Tweet(-80.1918, 25.7617, "Miami beach is awesome! 1", -0.8), // Florida
                new Tweet(-85.497, 30.997536, "Miami beach is awesome! 2", -0.2), // Florida
                new Tweet(-80.2918, 25.6617, "Miami beach is awesome! 3", 0.3), // Florida
                new Tweet(-81.1918, 25.7617, "Miami beach is awesome! 4", 0.5), // Florida
                new Tweet(-155.886774, 19.248084, "Hi lol 1", 0.0), // Hi
                new Tweet(-155.886774, 19.258084, "Hi lol 2", null) // Hi
        );
        Map<String, List<Tweet>> groupedTweets = groupTweetsByState(tweets, states);
        return calculateAverageSentiments(groupedTweets);
    }
}
