package dev.moudni.customersfrontappthymeleaf.repository;

import dev.moudni.customersfrontappthymeleaf.entites.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
