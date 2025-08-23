package com.microsv.user_service;

import com.microsv.user_service.entity.User;
import com.microsv.user_service.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

@SpringBootApplication
@EnableDiscoveryClient // đăng ký eureka
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
	@Bean
	@DependsOn("entityManagerFactory") // <-- THÊM DÒNG NÀY
	CommandLineRunner commandLineRunner(UserRepository userRepository) {
		return args -> {
			// Tạo và lưu user mẫu 1
			User user1 = new User();
			user1.setName("Tran Van B");
			user1.setEmail("b.tran@example.com");
			userRepository.save(user1);

			// Tạo và lưu user mẫu 2
			User user2 = new User();
			user2.setName("Le Thi C");
			user2.setEmail("c.le@example.com");
			userRepository.save(user2);

			System.out.println("Đã thêm 2 user mẫu vào CSDL!");
		};
	}
}
