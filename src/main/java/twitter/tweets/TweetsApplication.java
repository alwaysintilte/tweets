package twitter.tweets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TweetsApplication {

	public static void main(String[] args) {
		TweetParser.parseTweets();
		//SpringApplication.run(TweetsApplication.class, args);
	}

}