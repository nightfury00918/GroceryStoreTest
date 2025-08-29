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
}
