package session7;

class Product {
    protected String name;
    protected double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getProductDetails() {
        return "Product: " + name + ", Price: Rp" + price;
    }
}
