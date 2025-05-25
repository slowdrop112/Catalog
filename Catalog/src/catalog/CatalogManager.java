package catalog;

import java.util.ArrayList;
import java.util.List;

public class CatalogManager {
    private List<Student> studenti = new ArrayList<>();
    private List<Profesor> profesori = new ArrayList<>();
    private List<Materie> materii = new ArrayList<>();
    private List<Nota> note = new ArrayList<>();

    public void adaugaStudent(Student s) {
        studenti.add(s);
    }

    public void adaugaProfesor(Profesor p) {
        profesori.add(p);
    }

    public void adaugaMaterie(Materie m) {
        materii.add(m);
    }

    public void adaugaNota(Nota n) {
        note.add(n);
    }

    public List<Nota> getNoteStudent(String studentId) {
        List<Nota> rezultat = new ArrayList<>();
        for (Nota n : note) {
            if (n.getStudent().getId().equals(studentId)) {
                rezultat.add(n);
            }
        }
        return rezultat;
    }

    public double calculeazaMediaStudent(String studentId) {
        List<Nota> noteStudent = getNoteStudent(studentId);
        if (noteStudent.isEmpty()) return 0;
        double suma = 0;
        for (Nota n : noteStudent) {
            suma += n.getValoare();
        }
        return suma / noteStudent.size();
    }
}
