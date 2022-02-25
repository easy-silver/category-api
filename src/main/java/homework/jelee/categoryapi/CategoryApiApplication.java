package homework.jelee.categoryapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CategoryApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CategoryApiApplication.class, args);
	}

}
