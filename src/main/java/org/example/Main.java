package org.example;

import org.example.model.Product;
import org.example.model.Promotion;
import org.example.model.UnitType;
import org.example.scan.ScannedItem;
import org.example.service.Checkout;
import org.example.service.ReceiptPrinter;

import java.math.BigDecimal;
import java.util.List;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
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