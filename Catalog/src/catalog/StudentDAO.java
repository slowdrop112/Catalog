package catalog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public static void adaugaStudent(Student student) {
        String sql = "INSERT INTO studenti (id, nume, prenume) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getId());
            pstmt.setString(2, student.getNume());
            pstmt.setString(3, student.getPrenume());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Eroare la adăugarea studentului: " + e.getMessage());
        }
    }

    public static List<Student> getTotStudentii() {
        List<Student> studenti = new ArrayList<>();
        String sql = "SELECT * FROM studenti ORDER BY CAST(id AS INTEGER) ASC";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("id");
                String nume = rs.getString("nume");
                String prenume = rs.getString("prenume");
                studenti.add(new Student(nume, prenume, id));
            }

        } catch (SQLException e) {
            System.out.println("Eroare la citirea studenților: " + e.getMessage());
        }

        return studenti;
    }


    public static boolean existaStudentCuId(String id) {
        String sql = "SELECT COUNT(*) FROM studenti WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.out.println("Eroare la verificarea ID-ului studentului: " + e.getMessage());
            return true;
        }
    }
    public static void stergeStudent(String id) {
        String sql = "DELETE FROM studenti WHERE id = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Student șters cu succes.");
            } else {
                System.out.println("❌ Studentul cu ID-ul " + id + " nu există.");
            }

        } catch (SQLException e) {
            System.out.println("Eroare la ștergerea studentului: " + e.getMessage());
        }
    }

    public static Student getStudentById(String id) {
        String sql = "SELECT * FROM studenti WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String nume = rs.getString("nume");
                String prenume = rs.getString("prenume");
                return new Student(nume, prenume, id);
            }
        } catch (SQLException e) {
            System.out.println("Eroare la căutarea studentului: " + e.getMessage());
        }
        return null;
    }



}
