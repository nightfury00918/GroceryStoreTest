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
        // create temp CSV file
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
        // cleanup
        Files.deleteIfExists(tmp);
    }
}
