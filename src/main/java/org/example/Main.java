package org.example;

import org.example.Models.Order;
import org.example.Models.PaymentMethod;
import org.example.Optimizer.Optimizer;
import org.example.Parser.JsonParser;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String ordersPath = args[0];
        String paymentsPath = args[1];

        List<Order>orders = JsonParser.parseOrders(ordersPath);
        Map<String, PaymentMethod> methods = JsonParser.parseMethods(paymentsPath);

        Optimizer optimizer = new Optimizer(methods, orders);
        optimizer.optimize();

        System.out.println(optimizer.calculateBest());
    }
}
