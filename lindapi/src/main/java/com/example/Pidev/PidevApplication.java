package com.example.Pidev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.example.Pidev.client") // ✅ point to your client package

@SpringBootApplication
public class PidevApplication {

	public static void main(String[] args) {
		SpringApplication.run(PidevApplication.class, args);
	}
/*	@Autowired
	private ArticleRepository repository;
	@Bean
	ApplicationRunner init() {
		return (args) -> {
			// save
			repository.save(new Article(null, "Cement", "High-quality cement for construction", 12.50f, 500, "Building Material", "cement.jpg", 100));
			repository.save(new Article(null, "Steel Rods", "Reinforced steel rods for strong structures", 50.00f, 300, "Building Material", "steel_rods.jpg", 50));
			repository.save(new Article(null, "Bricks", "Durable red bricks for walls", 0.75f, 10000, "Building Material", "bricks.jpg", 500));
			repository.save(new Article(null, "Paint", "Weather-resistant exterior paint", 25.99f, 200, "Finishing", "paint.jpg", 30));
			// fetch
			repository.findAll().forEach(System.out::println);
		};
	}*/
}
