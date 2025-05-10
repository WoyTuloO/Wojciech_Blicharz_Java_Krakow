package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class JsonParser {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Order> parseOrders(String filePath) throws IOException {
        return mapper.readValue(new File(filePath), new TypeReference<List<Order>>() {});
    }

    public static Map<String, PaymentMethod> parseMethods(String filePath) throws IOException {
        List<PaymentMethod> methods = mapper.readValue(new File(filePath), new TypeReference<List<PaymentMethod>>() {});
        Map<String, PaymentMethod> map = new HashMap<>();
        for (PaymentMethod pm : methods) {
            map.put(pm.id, pm);
        }
        return map;
    }
}
