package pdp.odev;
import java.util.ArrayList;

/**
 * @author Sinan Ulusinan
 * @since 19 Nisan 2026
 * <p>
 * ilçeleri yönetir kisi sayisini belirleyen ana yapı birimi
 * </p>
 */

public class Sehir {
	private final String name;
    private final ArrayList<Ilce> ilceler;

    public Sehir(String name) {
        this.name = name;
        this.ilceler = new ArrayList<>();
    }

    // yeni ilce
    public void ilceEkle(Ilce i) {
        this.ilceler.add(i);
    }

    // yaslan metodu
    public void yaslanAll() {
        for (Ilce i : ilceler) {
            i.yaslanAll();
        }
    }

    //  güncel nüfus tutma
    public int getPeopleCount() {
        int totalNufus = 0;
        for (Ilce i : ilceler) {
            totalNufus += i.getPeopleCount();
        }
        return totalNufus;
    }

   
    public String getName() {return name; }
    public ArrayList<Ilce> getIlceler() {return ilceler; }
}
