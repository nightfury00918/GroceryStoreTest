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
