package pdp.odev;
import com.github.javafaker.Faker;

/**
 *
 * @author Sinan Ulusinan
 * @since 19 Nisan 2026
 * <p>
 * Oyun içindeki her kisiyi tanimlayan ve kisilerin yaslanmasini barindiran sınıf
 * </p>
 */


public class Kisi {

	// ID 
    private static int sayac = 1;
    // diger veriler
    private int id;
    private String name;
    private String surname;
    private int age;

    // baslangic
    public Kisi(Faker faker) {
        this.id = sayac++; 
        this.name = faker.name().firstName();
        this.surname = faker.name().lastName();
        this.age = faker.number().numberBetween(0, 51); 
    }

    // tur sonu eklenen nüfus
    public Kisi(Faker faker, boolean isNewborn) {
        this.id = sayac++;
        this.name = faker.name().firstName();
        this.surname = faker.name().lastName();
        this.age = 0; // yeni nüfus
    }

    // yaslanma metodu
    public void yaslan() {
        this.age++;
    }

    // getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public int getAge() { return age; }

    // rapordaki formatla gösterim icin - - yaptim
    @Override
    public String toString() {
        return this.id + "-" + this.name + " " + this.surname + " - " + this.age;
    }
	
}
