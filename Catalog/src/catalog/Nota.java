package catalog;

public class Nota {
    private Student student;
    private Materie materie;
    private double valoare;
    private TipNota tip;

    public Nota(Student student, Materie materie, double valoare, TipNota tip) {
        if (valoare < 1 || valoare > 10) {
            throw new IllegalArgumentException("Nota trebuie sa fie intre 1 si 10.");
        }
        this.student = student;
        this.materie = materie;
        this.valoare = valoare;
        this.tip = tip;
    }

    public Student getStudent() {
        return student;
    }

    public Materie getMaterie() {
        return materie;
    }

    public double getValoare() {
        return valoare;
    }

    public TipNota getTip() {
        return tip;
    }

    @Override
    public String toString() {
        return "Nota{" +
                "student=" + student.getNumeComplet() +
                ", materie=" + materie.getNume() +
                ", valoare=" + valoare +
                ", tip=" + tip +
                '}';
    }
}
