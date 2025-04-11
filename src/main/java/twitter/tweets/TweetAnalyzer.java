package twitter.tweets;

import java.util.*;

public class TweetAnalyzer {

    private static Map<String, Float> sentimentDictionary;

    public static void setSentimentsToTweets(List<Tweet> tweets, Map<String, Float> sentiments) {
        sentimentDictionary = new HashMap<>(sentiments);
        for(Tweet tweet: tweets) {
            tweet.setSentimentNumber(calculateSentiment(tweet.getText()));
        }
    }

    private static Double calculateSentiment(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }

        String[] words = text.split("\\s+");
        double totalSentiment = 0.0d;
        int metWords = 0;

        for (int i = 0; i < words.length; i++) {
            for (int phraseLength = 4; phraseLength >= 1; phraseLength--) {
                if (i + phraseLength <= words.length) {
                    String phrase = String.join(" ", Arrays.copyOfRange(words, i, i + phraseLength));
                    if (sentimentDictionary.containsKey(phrase)) {
                        metWords++;
                        totalSentiment += sentimentDictionary.get(phrase);
                        i += phraseLength - 1; // Перескакиваем на конец фразы
                        break;
                    }
                }
            }
        }
        return metWords == 0 ? null : totalSentiment/metWords;
    }

    public static Map<String, List<Tweet>> groupTweetsByState(List<Tweet> tweets, Map<String,State> states) {
        Map<String, List<Tweet>> groupedTweets = new HashMap<>();
        for (Tweet tweet : tweets) {
            String statePostalCode= StatePointChecker.findStateForPoint(tweet.getLongitude(), tweet.getLatitude(), states);
            if (statePostalCode != null) {
                tweet.setStatePostalCode(statePostalCode);
                groupedTweets.putIfAbsent(statePostalCode, new ArrayList<>());
                groupedTweets.get(statePostalCode).add(tweet);
            }
        }
        return groupedTweets;
    }

    public static Map<String, Double> calculateAverageSentiments(Map<String, List<Tweet>> groupedTweets) {
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
}
