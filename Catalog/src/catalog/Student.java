package catalog;

public class Student extends Persoana {
    public Student(String nume, String prenume, String id) {
        super(nume, prenume, id);
    }

    @Override
    public String getRol() {
        return "Student";
    }
}
