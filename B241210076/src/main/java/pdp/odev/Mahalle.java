package pdp.odev;
import java.util.ArrayList;

/**
 * * @author Sinan Ulusinan
 * @since 19 Nisan 2026
 * <p>
 * kisilerin listesini ve toplam nüfus verisini tutan en kücük yapı birimi
 * </p>
 */

public class Mahalle {
	
	private final String name;
    private final ArrayList<Kisi> people; // o mahallede yasayan kisiler

    public Mahalle(String name) {
        this.name = name;
        this.people = new ArrayList<>();
    }
    
    // kisi ekleme 
    public void peopleAdd(Kisi p) {
        this.people.add(p);
    }
    
    
    // yaslan metodu
    public void yaslanAll() {
        for (Kisi p : people) {
            p.yaslan();
        }
    }
    
    // güncel nüfus tutma
    public int getPeopleCount() {return people.size(); } 
    
    
    public String getName() { return name; }
    public ArrayList<Kisi> getPeople() {return people; }
    
}
