package dev.moudni.customersfrontappthymeleaf.repository;

import dev.moudni.customersfrontappthymeleaf.entites.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
