# GroceryStore Maven Project — Full revised version

This document contains the complete Maven project layout and the full contents of each file for the revised GroceryStore application (Java 24). Copy files into directories as shown and run `mvn test` or open in your IDE.

---

## Project layout

```
grocerystore/
├─ pom.xml
├─ .gitignore
├─ README.md
└─ src
   ├─ main
   │  └─ java
   │     └─ org
   │        └─ example
│              ├─ Main.java
│              ├─ OtherMainForCatalog.java
│              ├─ model
│              │  ├─ Product.java
│              │  ├─ UnitType.java
│              │  └─ Promotion.java
│              ├─ scan
│              │  └─ ScannedItem.java
│              └─ service
│                 ├─ Checkout.java
│                 ├─ ReceiptPrinter.java
│                 └─ ProductCatalog.java
   └─ test
      └─ java
         └─ org
            └─ example
                ├─ CheckoutTest.java
                └─ ProductCatalogTest.java

Optional file used by example (not required):
- sample-products.csv
```

---

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>GroceryStore</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>24</maven.compiler.source>
        <maven.compiler.target>24</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <junit.jupiter.version>5.10.0</junit.jupiter.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>${junit.jupiter.version}</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <pluginManagement>
            <plugins>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <version>3.0.0-M8</version>
                    <configuration>
                        <useModulePath>false</useModulePath>
                    </configuration>
                </plugin>
            </plugins>
        </pluginManagement>
    </build>

</project>
```

---

## .gitignore

```
target/
!.mvn/wrapper/maven-wrapper.jar
!**/src/main/**/target/
!**/src/test/**/target/

### IntelliJ IDEA ###
.idea/modules.xml
.idea/jarRepositories.xml
.idea/compiler.xml
.idea/libraries/
*.iws
*.iml
*.ipr

### Eclipse ###
.apt_generated
.classpath
.factorypath
.project
.settings
.springBeans
.sts4-cache

### NetBeans ###
/nbproject/private/
/nbbuild/
/dist/
/nbdist/
/.nb-gradle/
build/
!**/src/main/**/build/
!**/src/test/**/build/

### VS Code ###
.vscode/

### Mac OS ###
.DS_Store
```

---

## README.md

```
# GroceryStore

A small demo Maven Java project implementing a grocery checkout application.

This repository contains:
- An in-memory product catalog (add/update/remove and CSV import).
- Checkout logic supporting piece-based and weight-based products.
- "Buy X get Y free" promotions for piece items.
- Plain-text receipt printing and automated JUnit 5 tests.


## Features
- Product catalog with CSV import (project root `sample-products.csv`).
- Add/update/remove products at runtime (in-memory).
- Scan items by piece or weight and compute totals.
- Promotions support (buy X get Y free).
- Pretty plain-text receipt with line items, savings and total.


## Prerequisites
- Java 24 (or newer)
- Maven 3.9.9


## Build & test
From the project root (where `pom.xml` is located):

```bash
# download dependencies and run tests
mvn test
```


## Run examples
There are two small example entry points:
- `org.example.Main` — a tiny hard-coded demo.
- `org.example.OtherMainForCatalog` — demonstrates catalog usage and shows loading `sample-products.csv` from the project root.

Run with Maven exec plugin (from project root):

```bash
mvn -Dexec.mainClass="org.example.OtherMainForCatalog" \
  org.codehaus.mojo:exec-maven-plugin:3.0.0:java
```

Make sure your working directory contains `sample-products.csv` (project root). Alternatively, run the classes from your IDE (set working directory to project root).


## sample-products.csv
The catalog loader expects `sample-products.csv` in the project root. Format (no header required):

```
# id,name,unit,price
BANANA,Banana (kg),WEIGHT,1.99
MILK,Milk 1L,PIECE,1.25
EGGS,Eggs (dozen),PIECE,2.50
APPLE,Fresh Apple (kg),WEIGHT,3.50
CHIPS,Bag of Chips,PIECE,35.00
RICE,Rice (kg),WEIGHT,45.00
CHOC,Chocolate Bar,PIECE,2.99
```


## Screenshots
```
/docs/screenshots/result-1.png
/docs/screenshots/result-2.png
```


## Project structure
(Top-level important files)

```
pom.xml
sample-products.csv     # optional CSV loaded by OtherMainForCatalog
src/main/java/...       # source code
src/test/java/...       # unit tests
/docs/screenshots/      # put your screenshots here before pushing
```


## Pushing to Git (example)
From the project root:

```bash
git init
git add .
git commit -m "Initial GroceryStore project: catalog, checkout, promotions, receipts"
# add remote
git remote add origin git@github.com:<your-user>/<your-repo>.git
git push -u origin main
```


## Contributing
Small, self-contained PRs are welcome. Suggested workflow:
1. Fork the repo
2. Create a feature branch `feature/<name>`
3. Make changes and add tests
4. Open a PR describing the change


## Possible next improvements
- Persist products and orders to a database (JPA/Hibernate + H2/Postgres).
- Expose a REST API (Spring Boot) for product management and checkout.
- Add inventory counts and prevent checkout when out of stock.
- Add UI (React/Vue) or a simple CLI for operators.


## License
Choose a license for your repo (MIT, Apache-2.0, etc.) — add `LICENSE` file.


```

mvn test
```

Run example:
```
# from project root (requires compiled classes)
mvn -q -Dexec.mainClass="org.example.OtherMainForCatalog" org.codehaus.mojo:exec-maven-plugin:3.0.0:java
```

```

---

# Source files

> NOTE: package root is `org.example` — place files accordingly.

---

### src/main/java/com/example/grocerystore/model/Product.java

```java
package org.example.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private final String id;
    private final String name;
    private final UnitType unitType;
    private final BigDecimal pricePerUnit; // price per piece or per kilogram

    public Product(String id, String name, UnitType unitType, BigDecimal pricePerUnit) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.unitType = Objects.requireNonNull(unitType);
        this.pricePerUnit = Objects.requireNonNull(pricePerUnit);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public UnitType getUnitType() { return unitType; }
    public BigDecimal getPricePerUnit() { return pricePerUnit; }

    @Override
    public String toString() {
        return id + " - " + name + " (" + unitType + ") " + pricePerUnit;
    }
}
```

---

### src/main/java/com/example/grocerystore/model/UnitType.java

```java
package org.example.model;

public enum UnitType {
    PIECE, WEIGHT
}
```

---

### src/main/java/com/example/grocerystore/model/Promotion.java

```java
package org.example.model;

import java.util.Objects;

/**
 * Represents "buy X get Y free" for a particular product id.
 * Example: buy 1 get 1 free -> buy=1 free=1
 */
public class Promotion {
    private final String productId;
    private final int buy;
    private final int free;

    public Promotion(String productId, int buy, int free) {
        if (buy <= 0 || free < 0) throw new IllegalArgumentException("Invalid promotion quantities");
        this.productId = Objects.requireNonNull(productId);
        this.buy = buy;
        this.free = free;
    }

    public String getProductId() { return productId; }
    public int getBuy() { return buy; }
    public int getFree() { return free; }
}
```

---

### src/main/java/com/example/grocerystore/scan/ScannedItem.java

```java
package org.example.scan;

import org.example.model.Product;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * A single scanned item. For pieces, set quantity>=1 and weight=null. For weight-based items, set weight>0 and quantity=0.
 */
public class ScannedItem {
    private final Product product;
    private final int quantity; // number of pieces (0 for weight-based)
    private final BigDecimal weight; // in same units as product price (e.g., kilograms). null for piece items.

    private ScannedItem(Product product, int quantity, BigDecimal weight) {
        this.product = Objects.requireNonNull(product);
        this.quantity = quantity;
        this.weight = weight;
    }

    public static ScannedItem ofPieces(Product product, int quantity) {
        if (product.getUnitType() != org.example.model.UnitType.PIECE)
            throw new IllegalArgumentException("Product is not sold by piece");
        if (quantity <= 0) throw new IllegalArgumentException("quantity must be > 0");
        return new ScannedItem(product, quantity, null);
    }

    public static ScannedItem ofWeight(Product product, BigDecimal weight) {
        if (product.getUnitType() != org.example.model.UnitType.WEIGHT)
            throw new IllegalArgumentException("Product is not sold by weight");
        if (weight == null || weight.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("weight must be > 0");
        return new ScannedItem(product, 0, weight);
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public BigDecimal getWeight() { return weight; }
    public boolean isPiece() { return product.getUnitType() == org.example.model.UnitType.PIECE; }
    public boolean isWeight() { return product.getUnitType() == org.example.model.UnitType.WEIGHT; }
}
```

---

### src/main/java/com/example/grocerystore/service/Checkout.java

```java
package org.example.service;

import org.example.model.Product;
import org.example.model.Promotion;
import org.example.scan.ScannedItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Checkout service: collect scanned items and compute totals. No persistence.
 */
public class Checkout {

    // scanned items list (order preserved)
    private final List<ScannedItem> scans = new ArrayList<>();

    // promotions index by productId for quick lookup
    private final Map<String, Promotion> promotionsByProduct = new HashMap<>();

    public Checkout(Collection<Promotion> promotions) {
        if (promotions != null) {
            for (Promotion p : promotions) promotionsByProduct.put(p.getProductId(), p);
        }
    }

    public Checkout() { }

    public void addPromotion(Promotion p) {
        promotionsByProduct.put(p.getProductId(), p);
    }

    public void scan(ScannedItem item) {
        scans.add(item);
    }

    public List<ScannedItem> getScans() {
        return Collections.unmodifiableList(scans);
    }

    /**
     * Compute total price (after promotions) and return breakdown per product id.
     * Returns map productId -> PriceInfo
     */
    public Result calculateTotals() {
        // group pieces and weight items by product id
        Map<String, Integer> pieceCounts = new LinkedHashMap<>();
        Map<String, BigDecimal> weightSums = new LinkedHashMap<>();
        Map<String, Product> productLookup = new HashMap<>();

        for (ScannedItem s : scans) {
            Product p = s.getProduct();
            productLookup.putIfAbsent(p.getId(), p);
            if (s.isPiece()) {
                pieceCounts.put(p.getId(), pieceCounts.getOrDefault(p.getId(), 0) + s.getQuantity());
            } else {
                weightSums.put(p.getId(), weightSums.getOrDefault(p.getId(), BigDecimal.ZERO).add(s.getWeight()));
            }
        }

        BigDecimal total = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        BigDecimal savings = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

        Map<String, PriceLine> lines = new LinkedHashMap<>();

        // pieces
        for (Map.Entry<String, Integer> e : pieceCounts.entrySet()) {
            String pid = e.getKey();
            int qty = e.getValue();
            Product p = productLookup.get(pid);
            BigDecimal unitPrice = p.getPricePerUnit();
            Promotion promo = promotionsByProduct.get(pid);

            BigDecimal chargeableUnits;
            BigDecimal lineTotal;
            BigDecimal lineWithoutPromo = unitPrice.multiply(BigDecimal.valueOf(qty));
            if (promo != null) {
                int buy = promo.getBuy();
                int free = promo.getFree();
                int groupSize = buy + free;
                if (groupSize <= 0) groupSize = 1;
                int groups = qty / groupSize;
                int remainder = qty % groupSize;
                int chargeUnits = groups * buy + Math.min(remainder, buy);
                chargeableUnits = BigDecimal.valueOf(chargeUnits);
                lineTotal = unitPrice.multiply(chargeableUnits);
            } else {
                chargeableUnits = BigDecimal.valueOf(qty);
                lineTotal = unitPrice.multiply(chargeableUnits);
            }

            lineTotal = lineTotal.setScale(2, RoundingMode.HALF_UP);
            lines.put(pid, new PriceLine(p, BigDecimal.valueOf(qty), null, unitPrice, lineTotal, promo));
            total = total.add(lineTotal);
            savings = savings.add(lineWithoutPromo.subtract(lineTotal));
        }

        // weight-based
        for (Map.Entry<String, BigDecimal> e : weightSums.entrySet()) {
            String pid = e.getKey();
            BigDecimal totalWeight = e.getValue().setScale(3, RoundingMode.HALF_UP);
            Product p = productLookup.get(pid);
            BigDecimal unitPrice = p.getPricePerUnit();
            BigDecimal lineTotal = unitPrice.multiply(totalWeight).setScale(2, RoundingMode.HALF_UP);
            lines.put(pid, new PriceLine(p, null, totalWeight, unitPrice, lineTotal, null));
            total = total.add(lineTotal);
        }

        return new Result(lines, total.setScale(2,RoundingMode.HALF_UP), savings.setScale(2,RoundingMode.HALF_UP));
    }

    // Result and PriceLine classes used for structured return values
    public static class Result {
        private final Map<String, PriceLine> lines;
        private final BigDecimal total;
        private final BigDecimal savings;

        public Result(Map<String, PriceLine> lines, BigDecimal total, BigDecimal savings) {
            this.lines = lines;
            this.total = total;
            this.savings = savings;
        }

        public Map<String, PriceLine> getLines() { return lines; }
        public BigDecimal getTotal() { return total; }
        public BigDecimal getSavings() { return savings; }
    }

    public static class PriceLine {
        private final Product product;
        private final BigDecimal quantityPieces; // null when weight-based
        private final BigDecimal weight; // null when piece-based
        private final BigDecimal unitPrice;
        private final BigDecimal lineTotal;
        private final Promotion promotion;

        public PriceLine(Product product, BigDecimal quantityPieces, BigDecimal weight, BigDecimal unitPrice, BigDecimal lineTotal, Promotion promotion) {
            this.product = product;
            this.quantityPieces = quantityPieces;
            this.weight = weight;
            this.unitPrice = unitPrice;
            this.lineTotal = lineTotal;
            this.promotion = promotion;
        }

        public Product getProduct() { return product; }
        public BigDecimal getQuantityPieces() { return quantityPieces; }
        public BigDecimal getWeight() { return weight; }
        public BigDecimal getUnitPrice() { return unitPrice; }
        public BigDecimal getLineTotal() { return lineTotal; }
        public Promotion getPromotion() { return promotion; }
    }
}
```

---

### src/main/java/com/example/grocerystore/service/ReceiptPrinter.java

```java
package org.example.service;

import org.example.model.Promotion;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * Small pretty printer for the receipt (plain-text).
 */
public class ReceiptPrinter {

    private static final DecimalFormat moneyFmt = new DecimalFormat("#,##0.00");
    private static final DecimalFormat weightFmt = new DecimalFormat("#,##0.000");

    public static String print(Checkout.Result result) {
        StringBuilder sb = new StringBuilder();
        sb.append("===== Grocery Store Receipt =====\n");
        sb.append(String.format("%-20s %10s %10s %10s\n", "Item", "Qty/Weight", "Unit", "Line"));
        sb.append("-----------------------------------------------\n");

        for (Map.Entry<String, Checkout.PriceLine> e : result.getLines().entrySet()) {
            Checkout.PriceLine l = e.getValue();
            String name = l.getProduct().getName();
            String qtyDisplay;
            if (l.getQuantityPieces() != null) {
                qtyDisplay = l.getQuantityPieces().toBigInteger().toString();
            } else {
                qtyDisplay = weightFmt.format(l.getWeight());
            }
            String unit = moneyFmt.format(l.getUnitPrice());
            String line = moneyFmt.format(l.getLineTotal());
            sb.append(String.format("%-20s %10s %10s %10s\n", name, qtyDisplay, unit, line));
            Promotion p = l.getPromotion();
            if (p != null) {
                sb.append(String.format("   -> Promotion: buy %d get %d free\n", p.getBuy(), p.getFree()));
            }
        }
        sb.append("-----------------------------------------------\n");
        sb.append(String.format("%-42s %10s\n", "Savings:", moneyFmt.format(result.getSavings())));
        sb.append(String.format("%-42s %10s\n", "TOTAL:", moneyFmt.format(result.getTotal())));
        sb.append("===============================================\n");
        return sb.toString();
    }
}
```

---

### src/main/java/com/example/grocerystore/service/ProductCatalog.java

```java
package org.example.service;

import org.example.model.Product;
import org.example.model.UnitType;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Simple in-memory product catalog. Supports add/update/remove/list and CSV import.
 * CSV format (no header required): id,name,unitType,price
 * Example line: CHIPS,Bag of Chips,PIECE,35.00
 * Example line: RICE,Rice (kg),WEIGHT,45.00
 */
public class ProductCatalog {

    // preserve insertion order
    private final Map<String, Product> products = new LinkedHashMap<>();

    public ProductCatalog() { }

    public Optional<Product> getProduct(String id) {
        return Optional.ofNullable(products.get(id));
    }

    public Product addProduct(Product product) {
        Objects.requireNonNull(product, "product");
        return products.put(product.getId(), product);
    }

    /**
     * Replace existing product (same id) with updated product
     * @param product new product (must have same id if intended to replace)
     * @return previous Product or null
     */
    public Product updateProduct(Product product) {
        Objects.requireNonNull(product, "product");
        if (!products.containsKey(product.getId())) {
            throw new IllegalArgumentException("Product not found: " + product.getId());
        }
        return products.put(product.getId(), product);
    }

    public Product removeProduct(String id) {
        return products.remove(id);
    }

    public List<Product> listAll() {
        return new ArrayList<>(products.values());
    }

    public int size() {
        return products.size();
    }

    /**
     * Load products from CSV into the catalog. Existing products with same id are overwritten.
     * Lines starting with # or empty lines are ignored.
     */
    public void loadFromCsv(Path csvFile) throws IOException {
        List<String> lines = Files.readAllLines(csvFile, StandardCharsets.UTF_8);
        for (String raw : lines) {
            String line = raw.trim();
            if (line.isEmpty() || line.startsWith("#")) continue;
            String[] cols = line.split(",", -1);
            if (cols.length < 4) {
                // ignore malformed lines (could also throw)
                continue;
            }
            String id = cols[0].trim();
            String name = cols[1].trim();
            String unitStr = cols[2].trim().toUpperCase(Locale.ROOT);
            String priceStr = cols[3].trim();
            UnitType unit;
            try {
                unit = UnitType.valueOf(unitStr);
            } catch (IllegalArgumentException ex) {
                // skip unknown unit types
                continue;
            }
            BigDecimal price = new BigDecimal(priceStr);
            Product p = new Product(id, name, unit, price);
            products.put(id, p);
        }
    }
}
```

---

### src/main/java/com/example/grocerystore/Main.java

```java
package org.example;

import org.example.model.Product;
import org.example.model.Promotion;
import org.example.model.UnitType;
import org.example.scan.ScannedItem;
import org.example.service.Checkout;
import org.example.service.ReceiptPrinter;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Product chips = new Product("CHIPS", "Bag of Chips", UnitType.PIECE, new BigDecimal("35.00"));
        Product rice = new Product("RICE", "Rice (kg)", UnitType.WEIGHT, new BigDecimal("45.00"));

        Promotion buy1get1 = new Promotion("CHIPS", 1, 1);

        Checkout checkout = new Checkout(List.of(buy1get1));

        // Example scanning:
        checkout.scan(ScannedItem.ofPieces(chips, 3)); // 3 bags -> with buy1get1 => charge 2
        checkout.scan(ScannedItem.ofWeight(rice, new BigDecimal("2.5"))); // 2.5 kg rice

        Checkout.Result r = checkout.calculateTotals();
        System.out.println(ReceiptPrinter.print(r));
    }
}
```

---

### src/main/java/com/example/grocerystore/OtherMainForCatalog.java

```java
package org.example;

import org.example.model.Product;
import org.example.model.Promotion;
import org.example.model.UnitType;
import org.example.scan.ScannedItem;
import org.example.service.Checkout;
import org.example.service.ProductCatalog;
import org.example.service.ReceiptPrinter;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;

public class OtherMainForCatalog {
    public static void main(String[] args) throws IOException {
        ProductCatalog catalog = new ProductCatalog();

        // 1) Add products programmatically (e.g., from admin UI)
        Product chips = new Product("CHIPS", "Bag of Chips", UnitType.PIECE, new BigDecimal("35.00"));
        Product rice = new Product("RICE", "Rice (kg)", UnitType.WEIGHT, new BigDecimal("45.00"));
        Product apple = new Product("APPLE", "Fresh Apple (kg)", UnitType.WEIGHT, new BigDecimal("3.50"));
        Product chocolate = new Product("CHOC", "Chocolate Bar", UnitType.PIECE, new BigDecimal("2.99"));

        catalog.addProduct(chips);
        catalog.addProduct(rice);
        catalog.addProduct(apple);
        catalog.addProduct(chocolate);

        // 2) Or load from CSV (demonstration - CSV file path)
        // csv format: id,name,unit,price
        Path csv = Path.of("sample-products.csv");
        if (csv.toFile().exists()) {
            catalog.loadFromCsv(csv);
            System.out.println("Loaded products from " + csv);
        }

        // show current catalog
        System.out.println("Catalog contains " + catalog.size() + " products:");
        catalog.listAll().forEach(p -> System.out.println(" - " + p.getId() + ": " + p.getName() + " (" + p.getUnitType() + ") " + p.getPricePerUnit()));

        // 3) Use catalog items in checkout
        Promotion buy1get1 = new Promotion("CHIPS", 1, 1);
        Checkout checkout = new Checkout(List.of(buy1get1));

        // scan items by looking them up in the catalog
        catalog.getProduct("CHIPS").ifPresent(p -> checkout.scan(ScannedItem.ofPieces(p, 3))); // 3 bags
        catalog.getProduct("RICE").ifPresent(p -> checkout.scan(ScannedItem.ofWeight(p, new BigDecimal("2.5")))); // 2.5 kg
        catalog.getProduct("CHOC").ifPresent(p -> checkout.scan(ScannedItem.ofPieces(p, 5))); // 5 pieces

        var result = checkout.calculateTotals();
        System.out.println(ReceiptPrinter.print(result));
    }
}
```

---

# Tests

### src/test/java/com/example/grocerystore/CheckoutTest.java

```java
package org.example;

import org.example.model.Product;
import org.example.model.Promotion;
import org.example.model.UnitType;
import org.example.scan.ScannedItem;
import org.example.service.Checkout;
import org.example.service.ReceiptPrinter;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutTest {

    @Test
    void pieceProduct_updatesTotal() {
        Product chips = new Product("CHIPS", "Bag of Chips", UnitType.PIECE, new BigDecimal("35.00"));
        Checkout co = new Checkout();
        co.scan(ScannedItem.ofPieces(chips, 2));
        Checkout.Result r = co.calculateTotals();
        assertEquals(new BigDecimal("70.00"), r.getTotal());
    }

    @Test
    void weightProduct_updatesTotal() {
        Product rice = new Product("RICE", "Rice (kg)", UnitType.WEIGHT, new BigDecimal("45.00"));
        Checkout co = new Checkout();
        co.scan(ScannedItem.ofWeight(rice, new BigDecimal("1.2"))); // 1.2 * 45 = 54.00
        Checkout.Result r = co.calculateTotals();
        assertEquals(new BigDecimal("54.00"), r.getTotal());
    }

    @Test
    void promotions_buy1get1_and_buy2get1() {
        Product chips = new Product("CHIPS", "Bag of Chips", UnitType.PIECE, new BigDecimal("35.00"));
        Product candy = new Product("CANDY", "Candy Pack", UnitType.PIECE, new BigDecimal("10.00"));

        Promotion p1 = new Promotion("CHIPS", 1, 1); // buy1 get1
        Promotion p2 = new Promotion("CANDY", 2, 1); // buy2 get1

        Checkout co = new Checkout(List.of(p1, p2));
        co.scan(ScannedItem.ofPieces(chips, 3));
        co.scan(ScannedItem.ofPieces(candy, 7));

        Checkout.Result r = co.calculateTotals();
        assertEquals(new BigDecimal("120.00"), r.getTotal());
        String receipt = ReceiptPrinter.print(r);
        assertTrue(receipt.contains("Promotion"));
        assertTrue(receipt.contains("TOTAL:"));
    }

    @Test
    void receipt_prints_expected_lines() {
        Product chips = new Product("CHIPS", "Bag of Chips", UnitType.PIECE, new BigDecimal("35.00"));
        Product rice = new Product("RICE", "Rice (kg)", UnitType.WEIGHT, new BigDecimal("45.00"));
        Promotion p = new Promotion("CHIPS", 1, 1);

        Checkout co = new Checkout(List.of(p));
        co.scan(ScannedItem.ofPieces(chips, 2)); // charge 1
        co.scan(ScannedItem.ofWeight(rice, new BigDecimal("1.0")));

        var res = co.calculateTotals();
        var receipt = ReceiptPrinter.print(res);
        assertTrue(receipt.contains("Bag of Chips"));
        assertTrue(receipt.contains("Rice (kg)"));
        assertTrue(receipt.contains("Promotion"));
        assertTrue(receipt.contains("TOTAL:"));
    }
}
```

---

### src/test/java/com/example/grocerystore/ProductCatalogTest.java

```java
package org.example;

import org.example.model.Product;
import org.example.model.UnitType;
import org.example.service.ProductCatalog;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class ProductCatalogTest {

    @Test
    void addUpdateRemoveList() {
        ProductCatalog catalog = new ProductCatalog();
        Product p = new Product("X1", "Test Item", UnitType.PIECE, new BigDecimal("9.99"));
        catalog.addProduct(p);
        assertEquals(1, catalog.size());
        assertTrue(catalog.getProduct("X1").isPresent());

        Product updated = new Product("X1", "Test Item Updated", UnitType.PIECE, new BigDecimal("10.99"));
        catalog.updateProduct(updated);
        assertEquals("Test Item Updated", catalog.getProduct("X1").get().getName());

        Product removed = catalog.removeProduct("X1");
        assertNotNull(removed);
        assertFalse(catalog.getProduct("X1").isPresent());
    }

    @Test
    void loadFromCsvWorks() throws Exception {
        Path tmp = Files.createTempFile("catalog", ".csv");
        String csv = """
                # id,name,unit,price
                A1,Apple (kg),WEIGHT,3.50
                B1,Banana (kg),WEIGHT,1.99
                C1,Cookie Pack,PIECE,2.50
                """;
        Files.writeString(tmp, csv);
        ProductCatalog catalog = new ProductCatalog();
        catalog.loadFromCsv(tmp);
        assertEquals(3, catalog.size());
        assertTrue(catalog.getProduct("A1").isPresent());
        Files.deleteIfExists(tmp);
    }
}
```

---

## sample-products.csv (optional)

```
# sample products example
BANANA,Banana (kg),WEIGHT,1.99
MILK,Milk 1L,PIECE,1.25
EGGS,Eggs (dozen),PIECE,2.50
```

---