# Optimal Payment Promotions

## Features

* Optimize assignment of payment methods (cards, loyalty points) to orders
* Maximize total discount while respecting limits
* Support partial payment with loyalty points (≥10% of order value) with additional discount
* Load input data from JSON (orders, payment methods)
* JUnit 5 unit tests for both algorithm and JSON parser

## Prerequisites

* Java 11 or higher
* Maven 4
* JUnit 5
* Jackson library (`com.fasterxml.jackson.core:jackson-databind`)

## Running

The FAT-JAR file is located at 
```
Wojciech_Blicharz_Java_Krakow\out\artifacts\order_discount_algo_jar\order-discount-algo.jar
```

Execute the JAR with two arguments pointing to your JSON files:
```bash
java -jar order-discount-algo.jar path/to/orders.json path/to/payments.json
```

The program will output the optimal payment breakdown and the total amount spent.

## Building

1. Clone the repository:

   ```bash
   git clone https://github.com/WoyTuloO/Wojciech_Blicharz_Java_Krakow.git
   cd Wojciech_Blicharz_Java_Krakow
   ```
2. Build the executable JAR:

   ```bash
   mvn clean package
   ```

## Example Input

**orders.json**

```json
[
  {"id": "ORDER1", "value": "100.00", "promotions": ["mZysk"]},
  {"id": "ORDER2", "value": "200.00", "promotions": ["BosBankrut"]},
  {"id": "ORDER3", "value": "150.00", "promotions": ["mZysk", "BosBankrut"]},
  {"id": "ORDER4", "value": "50.00"}
]
```

**payments.json**

```json
[
  {"id": "PUNKTY",     "discount": "15", "limit": "100.00"},
  {"id": "mZysk",      "discount": "10", "limit": "180.00"},
  {"id": "BosBankrut", "discount": "5",  "limit": "200.00"}
]
```

## Example Output

```
PUNKTY 100.00
mZysk 165.00
BosBankrut 190.00
```
