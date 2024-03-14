package com.htw.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String name;
    private String id;
    private double price;

    // Konstruktor, Getter und Setter hier einfügen
}
