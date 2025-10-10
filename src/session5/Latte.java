package session5;

public class Latte extends Kopi {
    private boolean hasMilk;

    public Latte(String name, boolean isCold, String type, boolean hasMilk) {
        super(name, isCold, type);
        this.hasMilk = hasMilk;
    }

    @Override
    public void prepare() {
        System.out.println("Membuat latte dengan " + (hasMilk ? "susu tambahan" : "tanpa susu") + "...");
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Mengandung susu: " + (hasMilk ? "ya" : "tidak"));
    }
}

