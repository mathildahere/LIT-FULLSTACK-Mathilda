package session5;

public class Main {
    public static void main(String[] args) {
        Minuman espresso = new Kopi("Espresso", false, "robusta");
        Minuman latte = new Latte("Latte", false, "arabica", true);

        espresso.prepare();
        espresso.serve();
        espresso.displayInfo();

        System.out.println("\n---------------------\n");

        latte.prepare();
        latte.serve();
        latte.displayInfo();
    }
}
