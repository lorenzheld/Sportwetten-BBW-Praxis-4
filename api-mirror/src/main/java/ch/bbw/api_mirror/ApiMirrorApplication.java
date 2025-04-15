package ch.bbw.api_mirror;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ApiMirrorApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		System.setProperty("API_KEY", dotenv.get("API_KEY"));
		SpringApplication.run(ApiMirrorApplication.class, args);
	}

}