package session4;

public class Product {
    private String name;
    private double price;
    private int stock;

    public Product(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        } else {
            System.out.println("Nama tidak boleh kosong!");
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        } else {
            System.out.println("Harga tidak boleh negatif!");
        }
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock >= 0) {
            this.stock = stock;
        } else {
            System.out.println("Stok tidak boleh negatif!");
        }
    }

    public void showInfo() {
        System.out.println("Nama Produk : " + name);
        System.out.println("Harga       : Rp" + price);
        System.out.println("Stok        : " + stock);
        System.out.println("--------------------------");
    }
}
