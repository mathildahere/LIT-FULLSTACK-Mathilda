package session7;

class Electronics extends Product {
    private String brand;

    public Electronics(String name, double price, String brand) {
        super(name, price);
        this.brand = brand;
    }

    @Override
    public String getProductDetails() {
        return "Electronics: " + brand + " " + name + ", Price: Rp" + price;
    }
}
