package org.example;

import lombok.Data;

import java.util.Set;

@Data
public class Order {
    String id;
    double value;
    Set<String> promotions = Set.of();
}
