package org.example.service;

import org.example.model.Promotion;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.StringJoiner;

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
