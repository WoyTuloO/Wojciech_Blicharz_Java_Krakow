package org.example.Models;

import lombok.Data;

import java.util.Set;

@Data
public class Order {
    public String id;
    public double value;
    public Set<String> promotions = Set.of();
}
