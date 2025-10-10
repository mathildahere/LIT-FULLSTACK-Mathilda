package session5;

public class Kopi extends Minuman {
    private String type;

    public Kopi(String name, boolean isCold, String type) {
        super(name, isCold);
        this.type = type;
    }

    @Override
    public void prepare() {
        System.out.println("Menyeduh kopi " + type + "...");
    }

    @Override
    public void displayInfo() {
        System.out.println("Minuman: " + name);
        System.out.println("Suhu: " + (isCold ? "dingin" : "panas"));
        System.out.println("Tipe: " + type);
    }
}