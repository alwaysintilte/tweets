package twitter.tweets;

import java.io.IOException;
import java.util.*;

public class tweetAnalyzer {
    public static Map<String, List<Tweet>> groupTweetsByState(List<Tweet> tweets, Map<String,State> states) {
        Map<String, List<Tweet>> groupedTweets = new HashMap<>();
        for (Tweet tweet : tweets) {
            String statePostalCode=statePointChecker.findStateForPoint(tweet.getLongitude(), tweet.getLatitude(), states);
            if (statePostalCode != null) {
                tweet.setStatePostalCode(statePostalCode);
                groupedTweets.putIfAbsent(statePostalCode, new ArrayList<>());
                groupedTweets.get(statePostalCode).add(tweet);
            }
        }
        return groupedTweets;
    }

    public static Map<String, Double>  calculateAverageSentiments(Map<String, List<Tweet>> groupedTweets) {
        Map<String, Double> averageSentiments = new HashMap<>();
        for (Map.Entry<String, List<Tweet>> entry:groupedTweets.entrySet()) {
            Double averageSentiment = null;
            Double sumSentiment = null;
            Integer tweetNumber=0;
            for (Tweet tweet: entry.getValue()) {
                if (sumSentiment==null) {
                    if (tweet.getSentimentNumber()!=null){
                        sumSentiment=tweet.getSentimentNumber();
                        tweetNumber++;
                    }
                }
                else{
                    if (tweet.getSentimentNumber()!=null){
                        sumSentiment=sumSentiment+tweet.getSentimentNumber();
                        tweetNumber++;
                    }
                }
            }
            if (sumSentiment!=null){
                averageSentiment=sumSentiment/tweetNumber;
                averageSentiments.put(entry.getKey(), averageSentiment);
            }
        }
        return averageSentiments;
    }

    public static void main(String[] args) throws IOException {
        Map<String, State> states = JsonDeserializerMethods.JsonDeserializer("src/main/resources/static/states.json");
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
        for (Map.Entry<String, List<Tweet>> entry : groupedTweets.entrySet()) {
            System.out.println("State: " + entry.getKey());
            for (Tweet tweet : entry.getValue()) {
                System.out.println("  - " + tweet.text + " " + tweet.statePostalCode);
            }
        }
        Map<String, Double> averageSentiments = calculateAverageSentiments(groupedTweets);
        for (Map.Entry<String, Double> entry : averageSentiments.entrySet()) {
            System.out.println("State: " + entry.getKey() + " Sentiments: " + entry.getValue());
        }
    }
}
