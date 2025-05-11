package org.example;

import java.io.IOException;
import java.util.*;

public class Main {
    public static List<Order> orders;
    public static List<TotalPayment> allSolutions;
    public static void main(String[] args) throws IOException {
        String ordersPath = args[0];
        String paymentsPath = args[1];

        orders = JsonParser.parseOrders(ordersPath);
        Map<String, PaymentMethod> methods = JsonParser.parseMethods(paymentsPath);

        allSolutions = new ArrayList<>();
        List<TotalPayment> current = new ArrayList<>();
        backtracking(0, methods, current);


        System.out.println(calculateBest());
    }

    public static TotalPayment calculateBest(){
        TotalPayment best = null;
        double bestSum = Double.MAX_VALUE;
        for(TotalPayment t : allSolutions){
            if(t.getTotalMoneySpent() < bestSum){
                bestSum = t.getTotalMoneySpent();
                best = t;
            }
        }

        return best;
    }


    public static void backtracking(int orderIdx, Map<String, PaymentMethod> methods, List<TotalPayment> current) {

        if (orderIdx == orders.size()) {
            HashMap<String, Double> out = new HashMap<>();

            for (TotalPayment tp : current) {
                Collection<String> keys = tp.getMap().keySet();
                for (String key : keys) {
                    out.put(key, tp.getMap().get(key) + out.getOrDefault(key, 0.0));
                }

            }
            allSolutions.add(new TotalPayment(out));
            return;
        }

        Order order = orders.get(orderIdx);

        double orderVal = order.getValue();
        TotalPayment totalPayment = new TotalPayment(new HashMap<>());
        current.add(totalPayment);

        PaymentMethod points = methods.get("PUNKTY");

        if (points != null) {
            double fullPointsPay = orderVal * (1 - (points.getDiscount() / 100.0));
            if (points.getLimit() >= fullPointsPay) {
                totalPayment.put("PUNKTY", fullPointsPay);
                double oldPtsLimit = points.getLimit();
                points.setLimit(oldPtsLimit - fullPointsPay);

                backtracking(orderIdx + 1, methods, current);

                points.setLimit(oldPtsLimit);
                totalPayment.getMap().remove("PUNKTY");
            }
        }

        for (PaymentMethod card : methods.values()) {
            if ("PUNKTY".equals(card.getId())) continue;

            boolean isPromo = order.getPromotions().contains(card.getId());
            double discountFraction = isPromo ? card.getDiscount() / 100.0 : 0.0;
            double fullCardPay = orderVal * (1 - discountFraction);

            if (card.getLimit() >= fullCardPay) {
                totalPayment.put(card.getId(), fullCardPay);
                double oldCardLimit = card.getLimit();
                card.setLimit(oldCardLimit - fullCardPay);

                backtracking(orderIdx + 1, methods, current);

                card.setLimit(oldCardLimit);
                totalPayment.getMap().remove(card.getId());
            }
        }
        double threshold = orderVal * 0.10;
        if (points != null && points.getLimit() >= threshold && points.getLimit() < orderVal) {

            double discountedTotal = orderVal * 0.90;
            double oldPtsLimit = points.getLimit();
            double ptsPortion = Math.min(oldPtsLimit, discountedTotal);

            totalPayment.put("PUNKTY", ptsPortion);
            points.setLimit(oldPtsLimit - ptsPortion);

            double toPay = discountedTotal - ptsPortion;
            for (PaymentMethod card : methods.values()) {
                if ("PUNKTY".equals(card.getId())) continue;
                if (card.getLimit() >= toPay) {
                    double oldCardLimit = card.getLimit();
                    totalPayment.put(card.getId(),toPay);
                    card.setLimit(oldCardLimit - toPay);

                    backtracking(orderIdx + 1,methods, current);

                    card.setLimit(oldCardLimit);
                    totalPayment.getMap().remove(card.getId());
                }
            }
            points.setLimit(oldPtsLimit);
            totalPayment.getMap().remove("PUNKTY");
        }

    }



}
