package com.htw.basket.model;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Product {
    private String name;
    private String id;
    private double price;

    // Konstruktor, Getter und Setter hier einfügen
}
