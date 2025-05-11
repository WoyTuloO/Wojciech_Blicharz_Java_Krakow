
import org.example.Order;
import org.example.PaymentMethod;
import org.example.TotalPayment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.Main;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    @BeforeEach
    void setup() {
        Main.allSolutions = new ArrayList<>();
    }

    @Test
    void testSampleDataset() {
        List<Order> orders = new ArrayList<>();
        Order o1 = new Order(); o1.setId("ORDER1"); o1.setValue(100.0); o1.setPromotions(Set.of("mZysk")); orders.add(o1);
        Order o2 = new Order(); o2.setId("ORDER2"); o2.setValue(200.0); o2.setPromotions(Set.of("BosBankrut")); orders.add(o2);
        Order o3 = new Order(); o3.setId("ORDER3"); o3.setValue(150.0); o3.setPromotions(Set.of("mZysk", "BosBankrut")); orders.add(o3);
        Order o4 = new Order(); o4.setId("ORDER4"); o4.setValue(50.0); o4.setPromotions(Set.of()); orders.add(o4);
        Main.orders = orders;

        Map<String, PaymentMethod> methods = new HashMap<>();

        PaymentMethod points = new PaymentMethod();
        points.setId("PUNKTY");
        points.setDiscount(15);
        points.setLimit(100.0);
        methods.put("PUNKTY", points);

        PaymentMethod mZysk = new PaymentMethod();
        mZysk.setId("mZysk");
        mZysk.setDiscount(10);
        mZysk.setLimit(180.0);
        methods.put("mZysk", mZysk);

        PaymentMethod bos = new PaymentMethod();
        bos.setId("BosBankrut");
        bos.setDiscount(5);
        bos.setLimit(200.0);
        methods.put("BosBankrut", bos);

        Main.backtracking(0, methods, new ArrayList<>());
        TotalPayment res = Main.calculateBest();

        Map<String, Double> payments = res.getMap();

        assertEquals(100.0, payments.get("PUNKTY"), 0.001);
        assertEquals(165.0, payments.get("mZysk"), 0.001);
        assertEquals(190.0, payments.get("BosBankrut"), 0.001);
        assertEquals(455.0, res.getTotalMoneySpent(), 0.001);
    }

    @Test
    void testFullPointsOnly() {
        Order o1 = new Order();
        o1.setId("O1");
        o1.setValue(80.0);
        o1.setPromotions(Set.of());
        Main.orders = List.of(o1);

        Map<String, PaymentMethod> methods = new HashMap<>();

        PaymentMethod points = new PaymentMethod();
        points.setId("PUNKTY");
        points.setDiscount(20);
        points.setLimit(100.0);
        methods.put("PUNKTY", points);

        PaymentMethod card = new PaymentMethod();
        card.setId("C1");
        card.setDiscount(5);
        card.setLimit(50.0);
        methods.put("C1", card);

        Main.backtracking(0, methods, new ArrayList<>());
        TotalPayment res = Main.calculateBest();

        Map<String, Double> payments = res.getMap();

        assertEquals(64.0, payments.get("PUNKTY"), 0.001);
        assertEquals(64.0, res.getTotalMoneySpent(), 0.001);
    }


    @Test
    void testPointsDepletionAndCardFallback() {
        Order o1 = new Order();
        o1.setId("O1");
        o1.setValue(100.0);
        o1.setPromotions(Set.of());
        Main.orders = List.of(o1);

        Map<String, PaymentMethod> methods = new HashMap<>();

        PaymentMethod points = new PaymentMethod();
        points.setId("PUNKTY");
        points.setDiscount(15);
        points.setLimit(10.0);
        methods.put("PUNKTY", points);

        PaymentMethod card = new PaymentMethod();
        card.setId("CARD");
        card.setDiscount(0);
        card.setLimit(200.0);
        methods.put("CARD", card);

        Main.backtracking(0, methods, new ArrayList<>());
        TotalPayment res = Main.calculateBest();

        Map<String, Double> payments = res.getMap();

        assertEquals(10.0, payments.get("PUNKTY"), 0.001);
        assertEquals(80.0, payments.get("CARD"), 0.001);
        assertEquals(90.0, res.getTotalMoneySpent(), 0.001);
    }
}
