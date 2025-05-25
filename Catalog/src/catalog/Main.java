package catalog;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Inițializare DB și entități de bază
        DatabaseManager.init();
        System.out.println("=== Sistem Catalog Inițializat ===");

        // Profesor default pentru materiile de bază

        // Materii predefinite
// Materii predefinite
        String[] materiiBaza = {"Matematica", "Informatica", "Romana", "Biologie", "Sport", "Religie", "Engleza"};
        for (String numeMaterie : materiiBaza) {
            if (!MaterieDAO.existaMaterie(numeMaterie)) {
                Materie materie = new Materie(numeMaterie); // fără profesor
                MaterieDAO.adaugaMaterie(materie);
            }
        }


        Scanner scanner = new Scanner(System.in);
        boolean ruleaza = true;

        while (ruleaza) {
            System.out.println("\n=== MENIU ===");
            System.out.println("1. Adaugă student");
            System.out.println("2. Afișează toți studenții");
            System.out.println("3. Șterge student");
            System.out.println("4. Adaugă profesor");
            System.out.println("5. Afișează toți profesorii");
            System.out.println("6. Șterge profesor");
            System.out.println("7. Afișează materii");
            System.out.println("8. Adaugă Nota");
            System.out.println("9. Afișează notele unui student");
            System.out.println("10. Ieșire");
            System.out.print("Alege opțiunea: ");

            int optiune = scanner.nextInt();
            scanner.nextLine(); // consumă newline-ul

            switch (optiune) {
                case 1 -> {
                    String id;
                    while (true) {
                        System.out.print("ID student (număr > 0): ");
                        id = scanner.nextLine();
                        if (!id.matches("\\d+")) {
                            System.out.println("❌ ID invalid! Trebuie să fie un număr întreg pozitiv.");
                        } else if (StudentDAO.existaStudentCuId(id)) {
                            System.out.println("❌ ID deja existent! Alege un ID diferit.");
                        } else {
                            break;
                        }
                    }

                    System.out.print("Nume student: ");
                    String nume = scanner.nextLine();
                    System.out.print("Prenume student: ");
                    String prenume = scanner.nextLine();

                    Student student = new Student(nume, prenume, id);
                    StudentDAO.adaugaStudent(student);
                    System.out.println("✅ Student adăugat cu succes.");
                }

                case 2 -> {
                    System.out.println("=== Lista studenți ===");
                    for (Student s : StudentDAO.getTotStudentii()) {
                        System.out.println(s.getId() + ": " + s.getNumeComplet());
                    }
                }

                case 3 -> {
                    System.out.print("ID student de șters: ");
                    String id = scanner.nextLine();
                    StudentDAO.stergeStudent(id);
                }

                case 4 -> {
                    String id;
                    while (true) {
                        System.out.print("ID profesor (număr > 0): ");
                        id = scanner.nextLine();
                        if (!id.matches("\\d+")) {
                            System.out.println("❌ ID invalid! Trebuie să fie un număr întreg pozitiv.");
                        } else if (ProfesorDAO.existaProfesorCuId(id)) {
                            System.out.println("❌ ID deja existent! Alege un ID diferit.");
                        } else {
                            break;
                        }
                    }

                    System.out.print("Nume profesor: ");
                    String nume = scanner.nextLine();
                    System.out.print("Prenume profesor: ");
                    String prenume = scanner.nextLine();
                    System.out.print("Materie predată: ");
                    String materie = scanner.nextLine();

                    if (materie.isBlank()) {
                        System.out.println("❌ Materie lipsă! Profesorul nu a fost adăugat.");
                    } else if (!MaterieDAO.existaMaterie(materie)) {
                        System.out.println("❌ Materie inexistentă!");
                        System.out.println("Materii disponibile:");
                        for (String m : MaterieDAO.getNumeMaterii()) {
                            System.out.println("- " + m);
                        }
                    } else {
                        Profesor profesor = new Profesor(nume, prenume, id, materie);
                        ProfesorDAO.adaugaProfesor(profesor);
                        MaterieDAO.actualizeazaProfesorPentruMaterie(materie, profesor.getId());
                        System.out.println("✅ Profesor adăugat cu succes pentru materia: " + materie);
                    }
                }


                case 5 -> {
                    System.out.println("=== Lista profesori ===");

                    // Antet tabel
                    System.out.printf("%-5s | %-12s | %-12s | %-20s%n", "ID", "Nume", "Prenume", "Materie predată");
                    System.out.println("-------------------------------------------------------------");

                    for (Profesor p : ProfesorDAO.getTotiProfesorii()) {
                        System.out.printf("%-5s | %-12s | %-12s | %-20s%n",
                                p.getId(), p.getNume(), p.getPrenume(), p.getMaterieAlocata());
                    }
                }


                case 6 -> {
                    System.out.print("ID profesor de șters: ");
                    String id = scanner.nextLine();
                    ProfesorDAO.stergeProfesor(id);
                }

                case 7 -> {
                    System.out.println("=== Lista materii ===");
                    for (Materie m : MaterieDAO.getToateMateriile())
                    {
                        System.out.println("- " + m.getNume());
                    }
                }

                case 8 -> {
                    System.out.println("=== Adăugare notă ===");

                    // Selectare student
                    System.out.print("ID student: ");
                    String studentId = scanner.nextLine();
                    if (!StudentDAO.existaStudentCuId(studentId)) {
                        System.out.println("❌ Student inexistent!");
                        System.out.println("Studenți existenți:");
                        for (Student s : StudentDAO.getTotStudentii()) {
                            System.out.println("- " + s.getId() + ": " + s.getNumeComplet());
                        }
                        break;
                    }
                    Student student = StudentDAO.getStudentById(studentId);

                    // Selectare materie
                    System.out.print("Materie: ");
                    String materieNume = scanner.nextLine();
                    if (!MaterieDAO.existaMaterie(materieNume)) {
                        System.out.println("❌ Materie inexistentă!");
                        System.out.println("Materii disponibile:");
                        for (String m : MaterieDAO.getNumeMaterii()) {
                            System.out.println("- " + m);
                        }
                        break;
                    }
                    Materie materie = new Materie(materieNume);

                    // Selectare tip
                    System.out.print("Tip notă (EXAMEN / TEST): ");
                    String tipInput = scanner.nextLine().toUpperCase();
                    TipNota tip;
                    try {
                        tip = TipNota.valueOf(tipInput);
                    } catch (IllegalArgumentException e) {
                        System.out.println("❌ Tip invalid. Trebuie să fie EXAMEN sau TEST.");
                        break;
                    }

                    // Valoare notă
                    System.out.print("Notă (1 - 10): ");
                    String notaInput = scanner.nextLine();
                    double valoare;
                    try {
                        valoare = Double.parseDouble(notaInput);
                        if (valoare < 1 || valoare > 10) {
                            System.out.println("❌ Notă invalidă! Trebuie să fie între 1 și 10.");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Introdu un număr valid.");
                        break;
                    }

                    // Creăm nota și o salvăm
                    Nota nota = new Nota(student, materie, valoare, tip);
                    NotaDAO.adaugaNota(nota);
                }


                case 9 -> {
                    System.out.print("ID student: ");
                    String studentId = scanner.nextLine();

                    if (!StudentDAO.existaStudentCuId(studentId)) {
                        System.out.println("❌ Student inexistent!");
                        System.out.println("Studenți existenți:");
                        for (Student s : StudentDAO.getTotStudentii()) {
                            System.out.println("- " + s.getId() + ": " + s.getNumeComplet());
                        }
                        break;
                    }

                    Student student = StudentDAO.getStudentById(studentId);
                    var note = NotaDAO.getNotePentruStudent(student);

                    System.out.println("=== Note pentru " + student.getNumeComplet() + " ===");
                    System.out.printf("%-15s | %-8s | %-7s%n", "Materie", "Tip", "Valoare");
                    System.out.println("--------------------------------------------");

                    if (note.isEmpty()) {
                        System.out.println("Nu există note înregistrate pentru acest student.");
                    } else {
                        for (Nota n : note) {
                            System.out.printf("%-15s | %-8s | %-7.2f%n",
                                    n.getMaterie().getNume(), n.getTip().name(), n.getValoare());
                        }
                    }
                }

                case 10 -> {
                    ruleaza = false;
                    System.out.println("Ieșire din aplicație. La revedere!");
                }



                default -> System.out.println("❗ Opțiune invalidă.");
            }
        }

        scanner.close();
    }
}
