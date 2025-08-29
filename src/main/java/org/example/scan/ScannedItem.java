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
