package twitter.tweets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class TweetsApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(TweetsApplication.class, args);
	}

}
