package pdp.odev;
import java.util.ArrayList;

/**
 * @author Sinan Ulusinan
 * @since 19 Nisan 2026
 * <p>
 * mahalleleri yönetip kisi sayisini belirleyen orta yapı birimi
 * </p>
 */

public class Ilce {
	
	private final String name;
    private final ArrayList<Mahalle> mahalleler;

    public Ilce(String name) {
        this.name = name;
        this.mahalleler = new ArrayList<>();
    }

    // yeni mahalle
    public void mahalleAdd(Mahalle m) {
        this.mahalleler.add(m);
    }

    // yaslan metodu
    public void yaslanAll() {
        for (Mahalle m : mahalleler) {
            m.yaslanAll();
        }
    }

    
    // güncel nüfus tutma
    public int getPeopleCount() {
        int totalNufus = 0;
        for (Mahalle m : mahalleler) {
            totalNufus += m.getPeopleCount(); 
        }
        return totalNufus;
    }

   
    public String getName() {return name; }
    public ArrayList<Mahalle> getMahalleler() {return mahalleler; }
	
}
