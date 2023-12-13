package dev.moudni.inventoryservice;

import dev.moudni.inventoryservice.entites.Product;
import dev.moudni.inventoryservice.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(ProductRepository productRepository){
		return args -> {
			List<Product> products = List.of(
					Product.builder()
							.id(UUID.randomUUID().toString())
							.name("phone")
							.price(7000)
							.quantity(50)
							.build(),
					Product.builder()
							.id(UUID.randomUUID().toString())
							.name("pc")
							.price(10000)
							.quantity(70)
							.build(),
					Product.builder()
							.id(UUID.randomUUID().toString())
							.name("keyboard")
							.price(50)
							.quantity(200)
							.build()
			);
			productRepository.saveAll(products);
		};
	}
}
