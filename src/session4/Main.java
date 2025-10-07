package session4;

public class Main {
    public static void main(String[] args) {
        Product p1 = new Product("kopken", 25000, 10);
        Product p2 = new Product("roti o", 12000, 5);

        p1.showInfo();
        p2.showInfo();

        p1.setPrice(17000);
        p1.setStock(8);
        p1.showInfo();

        p2.setPrice(-5000);
        p2.setName("");
        p2.showInfo();
    }
}
