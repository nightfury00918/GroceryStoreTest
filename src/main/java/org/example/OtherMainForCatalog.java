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
        // Example file content (4 lines):
        // # sample catalog
        // BANANA,Banana (kg),WEIGHT,1.99
        // MILK,Milk 1L,PIECE,1.25
        Path csv = Path.of("sample-products.csv");
        if (csv.toFile().exists()) {
            catalog.loadFromCsv(csv);
            System.out.println("Loaded products from " + csv);
        }

        // show current catalog
        System.out.println("Catalog contains " + catalog.size() + " products:");
        catalog.listAll().forEach(p -> System.out.println(" - " + p.getId() + ": " + p.getName() + " (" + p.getUnitType() + ") " + p.getPricePerUnit()));

        // 3) Use catalog items in checkout
        // create some promotions and checkout
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
