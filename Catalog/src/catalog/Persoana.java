package catalog;

public abstract class Persoana {
    private String nume;
    private String prenume;
    private String id;

    public Persoana(String nume, String prenume, String id) {
        this.nume = nume;
        this.prenume = prenume;
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getId() {
        return id;
    }

    public abstract String getRol();

    public String getNumeComplet() {
        return prenume + " " + nume;
    }

    @Override
    public String toString() {
        return getRol() + ": " + getNumeComplet() + " (ID: " + id + ")";
    }
}
