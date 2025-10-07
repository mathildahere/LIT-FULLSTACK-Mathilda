package session3;

import java.util.ArrayList;
import java.util.Scanner;

public class ReceiptGenerator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ArrayList<String> header = new ArrayList<>();
        header.add("=============================");
        header.add("     TOKO LIT JAYA YEAH     ");
        header.add("     Jalan-jalan no. 123    ");
        header.add("=============================");

        ArrayList<String> footer = new ArrayList<>();
        footer.add("==============================");
        footer.add(" Terima kasih sudah belanja  ");
        footer.add("    Semoga sukses selalu!    ");
        footer.add("==============================");

        ArrayList<String> items = new ArrayList<>();
        ArrayList<Integer> prices = new ArrayList<>();
        ArrayList<Integer> qty = new ArrayList<>();

        System.out.println("=== INPUT DATA TRANSAKSI ===");
        while (true) {
            System.out.print("Masukkan nama barang (atau ketik 'selesai' untuk generate receipt): ");
            String item = sc.nextLine();

            if (item.equalsIgnoreCase("selesai")) {
                break;
            }

            if (items.contains(item)) {
                System.out.println("Barang dengan nama \"" + item + "\" sudah ada! Silakan masukkan nama lain.\n");
                continue;
            }

            System.out.print("Masukkan jumlah " + item + ": ");
            int jumlah = sc.nextInt();

            System.out.print("Masukkan harga satuan " + item + ": Rp");
            int harga = sc.nextInt();
            sc.nextLine();

            items.add(item);
            qty.add(jumlah);
            prices.add(harga);

            System.out.println("Barang berhasil ditambahkan!\n");
        }

        System.out.println("\n\n========== RECEIPT ==========");
        StringBuilder receipt = new StringBuilder();

        for (String line : header) {
            receipt.append(line).append("\n");
        }

        int total = 0;
        for (int i = 0; i < items.size(); i++) {
            int subtotal = prices.get(i) * qty.get(i);
            receipt.append(String.format("%-10s x%-2d Rp%8d\n", items.get(i), qty.get(i), subtotal));
            total += subtotal;
        }

        receipt.append("------------------------------\n");
        receipt.append(String.format("%-15s Rp%8d\n", "TOTAL", total));

        for (String line : footer) {
            receipt.append(line).append("\n");
        }

        System.out.println(receipt.toString());

        sc.close();
    }
}
