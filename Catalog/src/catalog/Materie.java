package catalog;

public class Materie {
    private String nume;

    public Materie(String nume) {
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    @Override
    public String toString() {
        return "Materie: " + nume;
    }
}
