package twitter.tweets;

import java.io.IOException;
import java.util.*;

public class TweetAnalyzer {

    private static TreeMap<String, Float> sentimentDictionary;
    public static int numOfIssue = 0;

    public static void setSentimentsToTweets(List<Tweet> tweets, Map<String, Float> sentiments) {
        sentimentDictionary = new TreeMap<>(sentiments);
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

        for (int i = 0; i < words.length; i++) { // По факту простой перебор с заглядыванием в TreeMap
            StringBuilder phraseBuilder = new StringBuilder();
            for (int j = i; j < Math.min(i + 4, words.length); j++) { // Собираем изначально самую большую последовательность слов
                if (!phraseBuilder.isEmpty()) {
                    phraseBuilder.append(" ");
                }
                phraseBuilder.append(words[j]);

                String phrase = phraseBuilder.toString();
                if (sentimentDictionary.containsKey(phrase)) {
                    metWords++;
                    totalSentiment += sentimentDictionary.get(phrase);
                    i = j; // конец найденной фразы
                    break;
                }
            }
        }
        return metWords == 0 ? null : totalSentiment/metWords;
    }

    public static Map<String, List<Tweet>> groupTweetsByState(List<Tweet> tweets, Map<String,State> states) {
        Map<String, List<Tweet>> groupedTweets = new HashMap<>();
        for (Tweet tweet : tweets) {
            String statePostalCode=statePointChecker.findStateForPoint(tweet.getLongitude(), tweet.getLatitude(), states);
            if (statePostalCode != null) {
                tweet.setStatePostalCode(statePostalCode);
                groupedTweets.putIfAbsent(statePostalCode, new ArrayList<>());
                groupedTweets.get(statePostalCode).add(tweet);
            } else {
                System.out.println("Ошибка присвоения штата");
                numOfIssue++;
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
