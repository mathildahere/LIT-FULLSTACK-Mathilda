package session7;

import java.util.ArrayList;
import java.util.List;

public class ECommerceSystem {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();

        products.add(new Book("Harry Pottern and The Prisoner of Azkaban", 150000, "J.K. Rowling"));
        products.add(new Electronics("Smartphone", 4000000, "Samsung"));
        products.add(new Book("Panggil Aku Kartini Saja", 20000, "Pramoedya Ananta Toer"));
        products.add(new Electronics("Laptop", 8000000, "ASUS"));

        // Polymorphism
        for (Product p : products) {
            System.out.println(p.getProductDetails());
        }
    }
}