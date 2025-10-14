package session7;

class Book extends Product {
    private String author;

    public Book(String name, double price, String author) {
        super(name, price);
        this.author = author;
    }

    @Override
    public String getProductDetails() {
        return "Book: " + name + " by " + author + ", Price: Rp" + price;
    }
}
