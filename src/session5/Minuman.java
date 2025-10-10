package session5;

public abstract class Minuman implements Drinkable {
    protected String name;
    protected boolean isCold;

    public Minuman(String name, boolean isCold) {
        this.name = name;
        this.isCold = isCold;
    }

    @Override
    public void serve() {
        System.out.println("Menyajikan " + name + (isCold ? " (dingin)" : " (panas)") + "...");
    }

    public abstract void displayInfo();
}
