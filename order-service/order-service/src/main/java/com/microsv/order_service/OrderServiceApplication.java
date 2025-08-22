package com.microsv.order_service;

import com.microsv.order_service.entity.Order;
import com.microsv.order_service.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(OrderRepository orderRepository) {
		return args -> {
			// Tạo order cho user có ID = 1
			Order order1 = new Order();
			order1.setProductName("Macbook Pro M4");
			order1.setUserId(1L); // Quan trọng: userId này phải tồn tại trong user_db
			orderRepository.save(order1);

			// Tạo order cho user có ID = 2
			Order order2 = new Order();
			order2.setProductName("Iphone 17 Pro Max");
			order2.setUserId(2L); // Quan trọng: userId này phải tồn tại trong user_db
			orderRepository.save(order2);

			System.out.println("Đã thêm 2 order mẫu vào CSDL!");
		};
	}
}
