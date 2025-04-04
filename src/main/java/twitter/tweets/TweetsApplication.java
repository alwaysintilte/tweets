package twitter.tweets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class TweetsApplication {

	public static void main(String[] args) throws IOException {
		List<Tweet> tweets = TweetParser.parseFile("snow_tweets2014.txt");
		Map<String, State> states = JsonMethods.JsonDeserializer("src/main/resources/static/states.json");
		TweetAnalyzer.setSentimentsToTweets(tweets, CsvDeserializer.getSentiments());

		Map<String, List<Tweet>> groupedTweets = TweetAnalyzer.groupTweetsByState(tweets, states);
		for (Map.Entry<String, List<Tweet>> entry : groupedTweets.entrySet()) {
			System.out.println("State: " + entry.getKey());
			for (Tweet tweet : entry.getValue()) {
				System.out.println("  - " + tweet.text + " " + tweet.statePostalCode);
			}
		}
		SpringApplication.run(TweetsApplication.class, args);
	}

}
