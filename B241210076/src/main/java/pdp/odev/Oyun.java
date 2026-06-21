package pdp.odev;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Sinan Ulusinan
 * @since 19 Nisan 2026
 * <p>
 * Simülasyonun ana sinifi. Ana yönetici sinif.
 * c# ödevlerindeki gibi main sinifinda manager sinifini cagirip projeyi baslatmak gibi biz de oyun sinifini cagircaz
 * </p>
 */

public class Oyun {
	private final ArrayList<Sehir> sehirler;
    private final Faker faker;
    private final Scanner scanner;

    public Oyun() {
        this.sehirler = new ArrayList<>();
        this.faker = new Faker();
        this.scanner = new Scanner(System.in);
    }

    public void baslat() {
        System.out.print("Oyunun calisacagi tur sayisini giriniz: ");
        int turCount = scanner.nextInt();
        scanner.nextLine(); 

        System.out.println("Sehir verilerini giriniz (18 25 79 seklinde yaziniz!): ");
        String giris = scanner.nextLine();
        
        createFirstCities(giris); // ilk sehirler
        
        
        // baslangic
        ekraniTemizle();
        System.out.println("--- BASLANGIÇ DURUMU ---");
        matrisGoster();
        System.out.println("\n1. turu baslatmak icin Enter'a basiniz...");
        scanner.nextLine();

        for (int i = 1; i <= turCount; i++) {
            turIslemleri(i); // ? 
            
            if (i < turCount) {
                System.out.println("\n" + (i + 1) + ". tur sonucu icin Enter'a basiniz...");
                scanner.nextLine();
            } else {
                System.out.println("\nOyun bitti!! Son Turun verileri icin Enter'a basiniz...");
                scanner.nextLine();
            }
        }

        sonEkran();
    }

    private void createFirstCities(String giris) {
        String[] parcalar = giris.split(" ");
        for (String parca : parcalar) {
            int sayi = Integer.parseInt(parca);
            int ilceSayisi = sayi / 10;
            int mahalleBaslangic = sayi % 10;

            // Mahalle sayisi
            int toplamMahalleCount = mahalleCountCheck(mahalleBaslangic, ilceSayisi);
            int mahallePerIlce = toplamMahalleCount / ilceSayisi;
            int yeniSayi = (ilceSayisi * 10) + toplamMahalleCount;

            // toplam nüfus dağılımı
            int mahalleBasiKisi = (int) Math.ceil((double) yeniSayi / toplamMahalleCount);

            // baslangic sehir+ilce+mahalle+kisi olusturma
            Sehir yeniSehir = new Sehir(faker.address().city());
            for (int j = 0; j < ilceSayisi; j++) {
                Ilce yeniIlce = new Ilce(faker.address().state());
                for (int k = 0; k < mahallePerIlce; k++) {
                    Mahalle yeniMahalle = new Mahalle(faker.address().cityPrefix() + " Mahallesi");
                    for (int n = 0; n < mahalleBasiKisi; n++) {
                        yeniMahalle.peopleAdd(new Kisi(faker));
                    }
                    yeniIlce.mahalleAdd(yeniMahalle);
                }
                yeniSehir.ilceEkle(yeniIlce);
            }
            sehirler.add(yeniSehir);
        }
    }
    
    private int mahalleCountCheck(int raw, int districts) {
    	int birler = raw;
        
        // birler basamagi 0 ise 1 den basla
        if (birler == 0) {
            birler = 1;
        }

        // ilce sayisi tam bölünene kadar 9 dan 1 e kadar say
        while (birler % districts != 0) {
            birler++;
            if (birler > 9) {
                birler = 1;
            }
        }
        return birler;
    }

    
    // tur icinde 3 tane islem var = a) nufus artisi b)yaslan c) kontrol
    private void turIslemleri(int turNo) {
        // a)nüfus artisi
        for (Sehir s : sehirler) {
            int nufus = s.getPeopleCount();
            int katsayi = (nufus / 10 % 10) + (nufus % 10);
            if (katsayi == 0) katsayi = 1;

            for (Ilce ilce : s.getIlceler()) {
                for (Mahalle m : ilce.getMahalleler()) {
                    int mevcutMahalleNufusu = m.getPeopleCount();
                    int yeniDogacakSayisi = (mevcutMahalleNufusu * katsayi) - mevcutMahalleNufusu;
                    
                    for (int j = 0; j < yeniDogacakSayisi; j++) {
                        m.peopleAdd(new Kisi(faker, true));
                    }
                }
            }
        }

        // b)yaslan
        for (Sehir s : sehirler) {
            s.yaslanAll();
        }

        // c)kontrol (sehir 1000 kisiyi gecti mi?)
        ArrayList<Sehir> yeniSehirler = new ArrayList<>();
        for (Sehir s : sehirler) {
            if (s.getPeopleCount() >= 1000) {
                Sehir yeniS = new Sehir(faker.address().city());
                int ilceSayisi = s.getIlceler().size();

                if (ilceSayisi > 1) {
                    // 1den fazla ilce var ise
                    int yeniyeGidecekCount = ilceSayisi / 2;
                    for (int j = 0; j < yeniyeGidecekCount; j++) {
                        Ilce tasinan = s.getIlceler().remove(s.getIlceler().size() - 1);
                        yeniS.ilceEkle(tasinan);
                    }
                } else {
                    // sadece 1 ilce var ise
                    Ilce eskiIlce = s.getIlceler().get(0);
                    Ilce yeniIlce = new Ilce(faker.address().state()); // yeni sehir de 1 ilce
                    yeniS.ilceEkle(yeniIlce);

                    int mahalleSayisi = eskiIlce.getMahalleler().size();

                    if (mahalleSayisi > 1) {
                        // mahalleleri böl
                        int yeniyeGidecekMahalle = mahalleSayisi / 2;
                        for (int j = 0; j < yeniyeGidecekMahalle; j++) {
                            Mahalle tasinanM = eskiIlce.getMahalleler().remove(eskiIlce.getMahalleler().size() - 1);
                            yeniIlce.mahalleAdd(tasinanM);
                        }
                    } else {
                        // sadece 1 mahalle var ise
                        Mahalle eskiMahalle = eskiIlce.getMahalleler().get(0);
                        Mahalle yeniMahalle = new Mahalle(faker.address().cityPrefix() + " Mahallesi"); // yeni ilce de 1 mahalle
                        yeniIlce.mahalleAdd(yeniMahalle);

                        int nufus = eskiMahalle.getPeopleCount();
                        int yeniyeGidecekNufus = nufus / 2;

                        // kisi göc
                        for (int j = 0; j < yeniyeGidecekNufus; j++) {
                            Kisi tasinanK = eskiMahalle.getPeople().remove(eskiMahalle.getPeople().size() - 1);
                            yeniMahalle.peopleAdd(tasinanK);
                        }
                    }
                }
                yeniSehirler.add(yeniS);
            }
        }
        sehirler.addAll(yeniSehirler);

        ekraniTemizle();
        System.out.println("--- TUR " + turNo + " SONUCU ---");
        matrisGoster();
    }

    private void matrisGoster() {
        for (int i = 0; i < sehirler.size(); i++) {
            System.out.print("[" + sehirler.get(i).getPeopleCount() + "]");
            if ((i + 1) % 5 == 0) System.out.println();
            else if (i < sehirler.size() - 1) System.out.print("-");
        }
        System.out.println();
    }

    private void sonEkran() {
        ekraniTemizle();
        System.out.println("--- OYUN SONU DURUMU ---");
        matrisGoster();
        
        System.out.print("\nGoruntulemek istediginiz sehrin Satir ve Sutun degerini girin (örnek olarak : 1 2)(ilk satir ve sütün 0'dan baslar): ");
        int satir = scanner.nextInt();
        int sutun = scanner.nextInt();
        
        int realIndex = (satir * 5) + sutun;
        
        if (realIndex >= 0 && realIndex < sehirler.size()) {
            detayliVeriDokum(sehirler.get(realIndex));
        } else {
            System.out.println("Gecersiz koordinat!");
        }
        
        System.out.println("\nCikmak icin herhangi bir tusa basin...");
        try { System.in.read(); } catch (Exception e) {}
    }

    private void detayliVeriDokum(Sehir s) {
        System.out.println("\nSehir: " + s.getName() + " - Nüfus: " + s.getPeopleCount());
        for (Ilce ilce : s.getIlceler()) {
            System.out.println("  Ilce: " + ilce.getName() + " - Nüfus: " + ilce.getPeopleCount());
            for (Mahalle m : ilce.getMahalleler()) {
                System.out.println("    Mahalle: " + m.getName() + " - Nüfus: " + m.getPeopleCount());
                System.out.println("    Kisiler:");
                for (Kisi k : m.getPeople()) {
                    System.out.println("      " + k.toString());
                }
            }
        }
    }

    private void ekraniTemizle() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
