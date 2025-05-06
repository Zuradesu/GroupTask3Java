import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class QueueCounter {
    public static void main(String[] args) {
        Queue<String> antrian = new LinkedList<>();
        Scanner scanner = new Scanner(System.in);
        int pilihan;

        do {
            System.out.println("\n--- Menu Antrian ---");
            System.out.println("1. Tambah Antrian");
            System.out.println("2. Hapus Antrian");
            System.out.println("3. Tampilkan Jumlah Item");
            System.out.println("4. Keluar");
            System.out.print("Pilih: ");
            pilihan = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan nama item: ");
                    String item = scanner.nextLine();
                    antrian.add(item);
                    System.out.println("Item ditambahkan.");
                    break;
                case 2:
                    if (!antrian.isEmpty()) {
                        String removed = antrian.poll();
                        System.out.println("Item dihapus: " + removed);
                    } else {
                        System.out.println("Antrian kosong.");
                    }
                    break;
                case 3:
                    System.out.println("Jumlah item dalam antrian: " + antrian.size());
                    break;
                case 4:
                    System.out.println("Program selesai.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        } while (pilihan != 4);
    }
}
