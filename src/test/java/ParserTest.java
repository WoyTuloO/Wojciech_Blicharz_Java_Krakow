package org.example.Parser;

import org.example.Models.Order;
import org.example.Models.PaymentMethod;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    void testParseOrdersAndMethods() throws Exception {
        File ordersFile = Files.createTempFile("orders", ".json").toFile();
        try (FileWriter writer = new FileWriter(ordersFile)) {
            writer.write("[\n");
            writer.write("  {\"id\": \"O1\", \"value\": \"10.5\", \"promotions\": [\"P1\"]},\n");
            writer.write("  {\"id\": \"O2\", \"value\": \"20.0\", \"promotions\": []}\n");
            writer.write("]");
        }

        File methodsFile = Files.createTempFile("methods", ".json").toFile();
        try (FileWriter writer = new FileWriter(methodsFile)) {
            writer.write("[\n");
            writer.write("  {\"id\": \"PUNKTY\", \"discount\": \"5\", \"limit\": \"50.0\"},\n");
            writer.write("  {\"id\": \"C1\", \"discount\": \"10\", \"limit\": \"100.0\"}\n");
            writer.write("]");
        }

        List<Order> orders = JsonParser.parseOrders(ordersFile.getAbsolutePath());
        Map<String, PaymentMethod> methods = JsonParser.parseMethods(methodsFile.getAbsolutePath());

        assertEquals(2, orders.size());
        Order first = orders.getFirst();
        assertEquals("O1", first.getId());
        assertEquals(10.5, first.getValue());
        assertEquals(Set.of("P1"), first.getPromotions());

        Order second = orders.get(1);
        assertEquals("O2", second.getId());
        assertEquals(20.0, second.getValue());
        assertTrue(second.getPromotions().isEmpty());

        assertEquals(2, methods.size());
        PaymentMethod pm1 = methods.get("PUNKTY");
        assertNotNull(pm1);
        assertEquals(5, pm1.getDiscount());
        assertEquals(50.0, pm1.getLimit());

        PaymentMethod pm2 = methods.get("C1");
        assertNotNull(pm2);
        assertEquals(10, pm2.getDiscount());
        assertEquals(100.0, pm2.getLimit());
    }
}
