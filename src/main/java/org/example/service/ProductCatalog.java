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
