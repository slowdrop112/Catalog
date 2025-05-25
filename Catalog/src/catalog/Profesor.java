package catalog;

public class Profesor extends Persoana {
    private String materieAlocata;

    public Profesor(String nume, String prenume, String id, String materieAlocata) {
        super(nume, prenume, id);
        this.materieAlocata = materieAlocata;
    }

    @Override
    public String getRol() {
        return "Profesor";
    }

    public String getMaterieAlocata() {
        return materieAlocata;
    }

    @Override
    public String toString() {
        return super.toString() + ", Materie: " + materieAlocata;
    }
}
