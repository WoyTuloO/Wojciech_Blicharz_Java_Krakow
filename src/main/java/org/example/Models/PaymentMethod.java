package org.example.Models;

import lombok.Data;

@Data
public class PaymentMethod {
    public String id;
    public double discount;
    public double limit;
}
