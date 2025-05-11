package org.example.Models;

import lombok.Data;

import java.util.HashMap;

@Data
public class TotalPayment {
    public HashMap<String, Double> map;

    public TotalPayment(HashMap<String, Double> totalPayments) {
        this.map = new HashMap<>(totalPayments);
    }
    public void put(String name, Double value){
        this.map.put(name, map.getOrDefault(name,0.0)+ value);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        map.forEach((k,v)->{
            sb.append(k).append(" ").append(v).append("\n");
        });

        return sb.toString();
    }

    public Double getTotalMoneySpent(){
        double totalMoneySpent = 0.0;
        for(Double value : map.values()){
            totalMoneySpent += value;
        }

        return totalMoneySpent;
    }
}
