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
