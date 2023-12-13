package dev.moudni.inventoryservice.repositories;

import dev.moudni.inventoryservice.entites.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
