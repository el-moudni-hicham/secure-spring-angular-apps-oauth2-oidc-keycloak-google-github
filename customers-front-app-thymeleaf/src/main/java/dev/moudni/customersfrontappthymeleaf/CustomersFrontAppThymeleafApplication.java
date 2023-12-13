package dev.moudni.customersfrontappthymeleaf;

import dev.moudni.customersfrontappthymeleaf.entites.Customer;
import dev.moudni.customersfrontappthymeleaf.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CustomersFrontAppThymeleafApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomersFrontAppThymeleafApplication.class, args);
	}

	@Bean
	CommandLineRunner start(CustomerRepository customerRepository){
		return args -> {
			List<Customer> customers = List.of(
				Customer.builder()
						.name("hicham el moudni")
						.email("him@gmail.com")
						.build(),
				Customer.builder()
						.name("ayoub barka")
						.email("you@gmail.com")
						.build(),

				Customer.builder()
				        .name("soufiane mouhtaram")
						.email("ouh@gmail.com")
						.build()
			);
			customerRepository.saveAll(customers);
		};
	}
}
