package org.example;

import lombok.Data;

@Data
public class PaymentMethod {
    String id;
    double discount;
    double limit;
}
