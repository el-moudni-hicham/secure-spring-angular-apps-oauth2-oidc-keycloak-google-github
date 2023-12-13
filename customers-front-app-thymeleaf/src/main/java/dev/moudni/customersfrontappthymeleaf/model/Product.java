package dev.moudni.customersfrontappthymeleaf.model;

import lombok.*;


@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor @Builder
public class Product {
    private String id;
    private String name;
    private double price;
    private int quantity;
}
