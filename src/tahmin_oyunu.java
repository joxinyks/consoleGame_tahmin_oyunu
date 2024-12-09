import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class tahmin_oyunu {
    public static void main(String[] args) {
        // joxinyks.txt dosyası içerisindeki logoyu, logoDosyasi adında bir değişkene atadık
        String logoDosyasi = "/Users/okanozturk/IntelliJ IDEA Projects/tahmin_oyunu/Tahmin Oyunu/src/joxinyks.txt";

        // ASCII logo ve karşılama ekranı
        try {
            File file = new File(logoDosyasi);
            Scanner scanner = new Scanner(file);

            // Logoyu satır satır, animasyonlu bir şekilde yazdır
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("Bir hata oluştu: " + e.getMessage());
                }
            }

            // Logo tamamlandıktan sonra 2 saniye bekleyeceğiz
            Thread.sleep(2000);

            // Karşılama mesajını göster
            System.out.println("\n\n" + "-".repeat(40));
            System.out.println("Hoşgeldiniz, Tahmin Oyunu'na!");
            System.out.println("Bu oyunda, rastgele bir sayıyı tahmin etmeye çalışacaksınız.");
            System.out.println("Başlamak için ENTER tuşuna basın...");
            System.out.println("-".repeat(40));

            // Kullanıcının oyuna başlaması için bir tuşa basmasını bekle
            System.in.read();  // ENTER tuşuna basmayı bekler
            System.out.println("\nOyuna Başlıyoruz!\n");

        } catch (FileNotFoundException e) {
            System.out.println("Dosya bulunamadı: " + logoDosyasi);
        } catch (Exception e) {
            System.out.println("Bir hata oluştu: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int puan = 100;
        int seviye = 1;
        boolean oyunDevam = true;

        System.out.println("***********************");
        System.out.println("*   TAHMİN OYUNUNA   *");
        System.out.println("*      HOŞGELDİN!    *");
        System.out.println("***********************");
        System.out.println("Kurallar:");
        System.out.println("1. Bilgisayarın tuttuğu sayıyı tahmin et.");
        System.out.println("2. Daha büyük veya küçük ipuçlarını takip et.");
        System.out.println("3. Doğru tahmin yaparak seviyeleri geç.");
        System.out.println("4. Her yanlış tahminde puan kaybedersin.\n");

        while (oyunDevam) {
            System.out.println("Seviye " + seviye + " - " + seviyeAralik(seviye) + " arasında bir sayı tuttum.");
            int hedefSayi = random.nextInt(maxAralik(seviye));
            boolean seviyeTamamlandi = false;

            while (!seviyeTamamlandi) {
                System.out.println("Tahmininiz: ");
                int tahmin = scanner.nextInt();

                if (tahmin == hedefSayi) {
                    System.out.println(renkliYazi("🎉 Doğru tahmin! Seviye tamamlandı! 🎉", "YEŞİL"));
                    seviyeTamamlandi = true;
                } else if (tahmin < hedefSayi) {
                    System.out.println(renkliYazi("Daha büyük bir sayı deneyin.", "SARI"));
                    puan -= 10;
                } else {
                    System.out.println(renkliYazi("Daha küçük bir sayı deneyin.", "SARI"));
                    puan -= 10;
                }

                if (puan <= 0) {
                    System.out.println(renkliYazi("😢 Puanınız tükendi! Oyunu kaybettiniz. 😢", "KIRMIZI"));
                    System.out.println("Doğru sayı: " + hedefSayi);
                    oyunDevam = false;
                    break;
                }
            }

            if (oyunDevam) {
                System.out.println("Puanınız: " + puan);
                System.out.print("Yeni seviyeye geçmek istiyor musunuz? (E/H): ");
                char cevap = scanner.next().toUpperCase().charAt(0);
                if (cevap == 'E') {
                    seviye++;
                } else {
                    oyunDevam = false;
                }
            }
        }

        System.out.println("Oyun sona erdi. Nihai puanınız: " + puan);
        System.out.println("Tekrar görüşmek üzere!");
    }

    // Maksimum aralık belirleme
    public static int maxAralik(int seviye) {
        switch (seviye) {
            case 1: return 10;
            case 2: return 50;
            case 3: return 100;
            default: return 100 + (seviye - 3) * 50;
        }
    }

    // Aralık bilgisini döndürme
    public static String seviyeAralik(int seviye) {
        return "1 ile " + maxAralik(seviye);
    }

    // Konsol renk kodları
    public static String renkliYazi(String mesaj, String renk) {
        String renkKodu;
        switch (renk.toUpperCase()) {
            case "YEŞİL": renkKodu = "\u001B[32m"; break;
            case "KIRMIZI": renkKodu = "\u001B[31m"; break;
            case "SARI": renkKodu = "\u001B[33m"; break;
            default: renkKodu = "\u001B[0m"; break;
        }
        return renkKodu + mesaj + "\u001B[0m";
    }
}